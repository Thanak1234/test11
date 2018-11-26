package repository.forms

import java.util.UUID

import auth.models.IdentityUser
import dto.{SirFormDto, SirFormTran, StrFormDto, StrFormTran}
import extensions.CustomColumnTypes._
import javax.inject.{Inject, Singleton}
import models.DbContext._
import models.forms.{StrForm, StrFormTable}
import models.{forms, _}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import repository.{ActivityRepository, FormAttachmentRepository, FormsRepository}
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._
import utils.{Filter, FilterSort, Sort}

import scala.concurrent.ExecutionContext

@Singleton
class StrFormRepository @Inject() (
                                    formsRepo: FormsRepository,
                                    activityRepo: ActivityRepository,
                                    sirAttachmentRepo: FormAttachmentRepository
                                  )(implicit ec: ExecutionContext) {

  def list(filterSort: FilterSort) = amlContext run {
    val search = filterSort.query.getOrElse("")
    val filterFields = filterSort.filter match {
      case Some(filters) => Json.parse(filters).validate[Seq[Filter]].get
      case None => Seq.empty
    }

    val orderFields = filterSort.sort match {
      case Some(sorts) => Json.parse(sorts).validate[Seq[Sort]].get
      case None => Seq.empty
    }

    val baseQuery = (QueryTables.strFormQuery join QueryTables.playerQuery).on({case (f, p) => f.playerId === p.id})
        .filter(q => buildColumnFilters(filterFields, q._1, q._2) && buildSearchQueryFilters(Option(s"%$search%"), q._1, q._2))

    val sortQuery = buildSort(baseQuery, orderFields)

    val selectQuery = sortQuery.map({case (form, player) => (
        form.id ::
        form.strNo ::
        form.sirNo ::
        form.strArea ::
        form.playerId ::
        form.strMethodVerify ::
        form.strCurrentRelationship ::
        form.strAffiliationRelationship ::
        form.strTypeSuspiciousActivities ::
        form.strTranDateFrom ::
        form.strTranDateTo ::
        form.ctrFormId ::
        form.ctrFormNo ::
        form.strAmount ::
        form.strSuspiciousIncidence ::
        form.strWatchlist ::
        form.strStaffId ::
        form.strStaffDepartment ::
        form.strDocumentNo ::
        form.submission ::
        form.status ::
        form.recievedDate ::
        form.recievedBy ::
        form.review ::
        form.strAffiliationRelationshipOthers ::
        form.strCurrentRelationshipOthers ::
        form.strTypeSuspiciousActivitiesOthers ::
        form.strMethodVerifyOthers ::
        form.strTransactionType ::
        form.playerWith ::
        form.createdDate ::
        form.createdBy ::
        form.modifiedDate ::
        form.modifiedBy ::
        player.playerName ::
        player.playerCardType ::
        player.driverLicense ::
        player.gercCardNo ::
        player.idCard ::
        player.passport ::
        player.others ::
        player.noCard ::
        player.dob :: HNil
    ).mapTo[StrFormDto]})

    for {
      totalRecords <- selectQuery.length.result
      records <- selectQuery
        .drop(filterSort.start.getOrElse(0))
        .take(filterSort.limit.getOrElse(0))
        .result
    } yield StrFormResource(records, totalRecords)
  }

  def buildColumnFilters(filters: Seq[Filter], form: StrFormTable, player: PlayerTable) = {
    val dateFormat = "MM/dd/yyyy"
    val columnsFilter = filters.map(c => {
      if(c.operator == Some("lt"))
        c.value.map(form.strTranDateFrom <= DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
      else if(c.operator == Some("gt"))
        c.value.map(form.strTranDateFrom >= DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
      else
        c.value.map(form.strTranDateFrom === DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
    }).toList

    columnsFilter
      .collect({case Some(c) => c})
      .reduceLeftOption(_ && _)
      .getOrElse(Some(true): Rep[Option[Boolean]])
  }

  def buildSort(query: Query[(StrFormTable, PlayerTable),
    (StrFormTable#TableElementType, PlayerTable#TableElementType), Seq],
                orderFields: Seq[Sort]) = orderFields.isEmpty match {
    case false => {
      orderFields.headOption match {
        case Some(order) => {
          val property = order.property
          val direction = order.direction
          query.sortBy(s => {
            property match {
              case Some("strNo") if direction == Some("ASC") => s._1.strNo.asc
              case Some("strNo") if direction == Some("DESC") => s._1.strNo.desc
              case _ => s._1.sirNo.desc
            }
          })
        }
        case None => query
      }
    }
    case true => query
  }

  def buildSearchQueryFilters(search: Option[String], form: StrFormTable, player: PlayerTable) = List(
    search.map(form.strNo like _)
  ).collect({case Some(c) => c}).reduceLeftOption(_ || _).getOrElse(Some(true): Rep[Option[Boolean]])

  def save(formTran: StrFormTran, identity: IdentityUser) = amlContext.run {
    val current = new DateTime()
    val ctrFormNo = formTran.ctrFormId match {
      case Some(id) => formTran.ctrFormNo
      case None => None
    }

    val sirForm = StrForm(
      formTran.id,
      formTran.strNo,
      formTran.sirNo,
      formTran.strArea,
      formTran.playerId,
      formTran.strMethodVerify,
      formTran.strCurrentRelationship,
      formTran.strAffiliationRelationship,
      formTran.strTypeSuspiciousActivities,
      formTran.strTranDateFrom,
      formTran.strTranDateTo,
      formTran.ctrFormId,
      ctrFormNo,
      formTran.strAmount,
      formTran.strSuspiciousIncidence,
      formTran.strWatchlist,
      formTran.strStaffId,
      formTran.strStaffDepartment,
      formTran.strDocumentNo,
      formTran.submission,
      Option("Active"),
      formTran.recievedDate,
      formTran.recievedBy,
      formTran.review,
      formTran.strAffiliationRelationshipOthers,
      formTran.strCurrentRelationshipOthers,
      formTran.strTypeSuspiciousActivitiesOthers,
      formTran.strMethodVerifyOthers,
      formTran.strTransactionType,
      formTran.playerWith,
      None,
      None,
      Option(current),
      Option(identity.id))

    updateStrForm(sirForm).flatMap(f => {
      sirAttachmentRepo.updateFormAttachment(formTran.newRecords.getOrElse(Seq.empty), sirForm.id)
      sirAttachmentRepo.deleteFormAttachment(formTran.removeRecords.getOrElse(Seq.empty))
      val activity = Activity(UUID.randomUUID(), sirForm.id, "Edit", formTran.comment, current, Option(identity.id))
      activityRepo.insertActivity(activity)
    })

  }

  def insertStrForm(strForm: StrForm) = QueryTables.strFormQuery += strForm
  def updateStrForm(strForm: StrForm) = (QueryTables.strFormQuery.filter(q => q.id === strForm.id)
    .map(m => (
      m.strNo ::
      m.sirNo ::
      m.strArea ::
      m.playerId ::
      m.strMethodVerify ::
      m.strCurrentRelationship ::
      m.strAffiliationRelationship ::
      m.strTypeSuspiciousActivities ::
      m.strTranDateFrom ::
      m.strTranDateTo ::
      m.ctrFormId ::
      m.ctrFormNo ::
      m.strAmount ::
      m.strSuspiciousIncidence ::
      m.strWatchlist ::
      m.strStaffId ::
      m.strStaffDepartment ::
      m.strDocumentNo ::
      m.submission ::
      m.status ::
      m.recievedDate ::
      m.recievedBy ::
      m.strAffiliationRelationshipOthers ::
      m.strCurrentRelationshipOthers ::
      m.strTypeSuspiciousActivitiesOthers ::
      m.strMethodVerifyOthers ::
      m.strTransactionType ::
      m.playerWith ::
      m.modifiedDate ::
      m.modifiedBy :: HNil
    )).update((
    strForm.strNo ::
    strForm.sirNo ::
    strForm.strArea ::
    strForm.playerId ::
    strForm.strMethodVerify ::
    strForm.strCurrentRelationship ::
    strForm.strAffiliationRelationship ::
    strForm.strTypeSuspiciousActivities ::
    strForm.strTranDateFrom ::
    strForm.strTranDateTo ::
    strForm.ctrFormId ::
    strForm.ctrFormNo ::
    strForm.strAmount ::
    strForm.strSuspiciousIncidence ::
    strForm.strWatchlist ::
    strForm.strStaffId ::
    strForm.strStaffDepartment ::
    strForm.strDocumentNo ::
    strForm.submission ::
    strForm.status ::
    strForm.recievedDate ::
    strForm.recievedBy ::
    strForm.strAffiliationRelationshipOthers ::
    strForm.strCurrentRelationshipOthers ::
    strForm.strTypeSuspiciousActivitiesOthers ::
    strForm.strMethodVerifyOthers ::
    strForm.strTransactionType ::
    strForm.playerWith ::
    strForm.modifiedDate ::
    strForm.modifiedBy :: HNil))
    )
}

