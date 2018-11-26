package repository.forms

import java.util.UUID

import auth.models.IdentityUser
import dto.{SirFormDto, SirFormTran}
import javax.inject.{Inject, Singleton}
import repository.{ActivityRepository, FormAttachmentRepository, FormsRepository}
import models.DbContext._
import models.{Activity, PlayerTable, QueryTables, SirFormResource}
import models.forms.{SirForm, SirFormTable}
import org.joda.time.DateTime
import slick.collection.heterogeneous.HNil
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import utils.{Filter, FilterSort, Sort}

import scala.concurrent.ExecutionContext

@Singleton
class SirFormRepository @Inject() (
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

    val baseQuery = (QueryTables.sirFormQuery join QueryTables.playerQuery).on({case (f, p) => f.playerId === p.id})
        .filter(q => buildColumnFilters(filterFields, q._1, q._2) && buildSearchQueryFilters(Option(s"%$search%"), q._1, q._2))

    val sortQuery = buildSort(baseQuery, orderFields)

    val selectQuery = sortQuery.map({case (form, player) => (
        form.id ::
        form.sirNo ::
        form.sirArea ::
        form.playerId ::
        form.sirMethodVerify ::
        form.sirCurrentRelationship ::
        form.sirAffiliationRelationship ::
        form.sirTypeSuspiciousActivities ::
        form.sirTranDateFrom ::
        form.sirTranDateTo ::
        form.ctrFormId ::
        form.ctrFormNo ::
        form.sirAmount ::
        form.sirSuspiciousIncidence ::
        form.sirWatchlist ::
        form.sirStaffId ::
        form.sirStaffDepartment ::
        form.sirDocumentNo ::
        form.submission ::
        form.status ::
        form.recievedDate ::
        form.recievedBy ::
        form.review ::
        form.sirAffiliationRelationshipOthers ::
        form.sirCurrentRelationshipOthers ::
        form.sirTypeSuspiciousActivitiesOthers ::
        form.sirMethodVerifyOthers ::
        form.isCloneToSTR ::
        form.sirTransactionType ::
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
    ).mapTo[SirFormDto]})

    for {
      totalRecords <- selectQuery.length.result
      records <- selectQuery
        .drop(filterSort.start.getOrElse(0))
        .take(filterSort.limit.getOrElse(0))
        .result
    } yield SirFormResource(records, totalRecords)
  }

  def buildColumnFilters(filters: Seq[Filter], form: SirFormTable, player: PlayerTable) = {
    val dateFormat = "MM/dd/yyyy"
    val columnsFilter = filters.map(c => {
      if(c.operator == Some("lt"))
        c.value.map(form.sirTranDateFrom <= DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
      else if(c.operator == Some("gt"))
        c.value.map(form.sirTranDateFrom >= DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
      else
        c.value.map(form.sirTranDateFrom === DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
    }).toList

    columnsFilter
      .collect({case Some(c) => c})
      .reduceLeftOption(_ && _)
      .getOrElse(Some(true): Rep[Option[Boolean]])
  }

  def buildSort(query: Query[(SirFormTable, PlayerTable),
    (SirFormTable#TableElementType, PlayerTable#TableElementType), Seq],
                orderFields: Seq[Sort]) = orderFields.isEmpty match {
    case false => {
      orderFields.headOption match {
        case Some(order) => {
          val property = order.property
          val direction = order.direction
          query.sortBy(s => {
            property match {
              case Some("sirNo") if direction == Some("ASC") => s._1.sirNo.asc
              case Some("sirNo") if direction == Some("DESC") => s._1.sirNo.desc
              case _ => s._1.sirNo.desc
            }
          })
        }
        case None => query
      }
    }
    case true => query
  }

  def buildSearchQueryFilters(search: Option[String], form: SirFormTable, player: PlayerTable) = List(
    search.map(form.sirNo like _)
  ).collect({case Some(c) => c}).reduceLeftOption(_ || _).getOrElse(Some(true): Rep[Option[Boolean]])

  def save(formTran: SirFormTran, identity: IdentityUser) = amlContext.run {
    val current = new DateTime()
    val ctrFormNo = formTran.ctrFormId match {
      case Some(id) => formTran.ctrFormNo
      case None => None
    }

    formTran.id match {
      case Some(id) => {
        val sirForm = SirForm(
          formTran.id,
          formTran.sirNo,
          formTran.sirArea,
          formTran.playerId,
          formTran.sirMethodVerify,
          formTran.sirCurrentRelationship,
          formTran.sirAffiliationRelationship,
          formTran.sirTypeSuspiciousActivities,
          formTran.sirTranDateFrom,
          formTran.sirTranDateTo,
          formTran.ctrFormId,
          ctrFormNo,
          formTran.sirAmount,
          formTran.sirSuspiciousIncidence,
          formTran.sirWatchlist,
          formTran.sirStaffId,
          formTran.sirStaffDepartment,
          formTran.sirDocumentNo,
          formTran.submission,
          Option("Active"),
          formTran.recievedDate,
          formTran.recievedBy,
          formTran.review,
          formTran.sirAffiliationRelationshipOthers,
          formTran.sirCurrentRelationshipOthers,
          formTran.sirTypeSuspiciousActivitiesOthers,
          formTran.sirMethodVerifyOthers,
          formTran.isCloneToSTR,
          formTran.sirTransactionType,
          formTran.playerWith,
          None,
          None,
          Option(current),
          Option(identity.id))

        updateSirForm(sirForm).flatMap(f => {
          sirAttachmentRepo.updateFormAttachment(formTran.newRecords.getOrElse(Seq.empty), sirForm.id)
          sirAttachmentRepo.deleteFormAttachment(formTran.removeRecords.getOrElse(Seq.empty))
          val activity = Activity(UUID.randomUUID(), sirForm.id, "Edit", formTran.comment, current, Option(identity.id))
          activityRepo.insertActivity(activity)
        })
      }
      case None => {
        formsRepo.getFormMax("SIR").flatMap(max => {
          val no = max + 1
          val formNo = Option(s"SIR-%06d".format(no))
          val sirForm = SirForm(
            Option(UUID.randomUUID()),
            formNo,
            formTran.sirArea,
            formTran.playerId,
            formTran.sirMethodVerify,
            formTran.sirCurrentRelationship,
            formTran.sirAffiliationRelationship,
            formTran.sirTypeSuspiciousActivities,
            formTran.sirTranDateFrom,
            formTran.sirTranDateTo,
            formTran.ctrFormId,
            ctrFormNo,
            formTran.sirAmount,
            formTran.sirSuspiciousIncidence,
            formTran.sirWatchlist,
            formTran.sirStaffId,
            formTran.sirStaffDepartment,
            formTran.sirDocumentNo,
            formTran.submission,
            Option("Active"),
            formTran.recievedDate,
            formTran.recievedBy,
            Option(false),
            formTran.sirAffiliationRelationshipOthers,
            formTran.sirCurrentRelationshipOthers,
            formTran.sirTypeSuspiciousActivitiesOthers,
            formTran.sirMethodVerifyOthers,
            Option(false),
            formTran.sirTransactionType,
            formTran.playerWith,
            Option(current),
            Option(identity.id),
            None,
            None)
          insertSirForm(sirForm).flatMap(f => {
            sirAttachmentRepo.updateFormAttachment(formTran.newRecords.getOrElse(Seq.empty), sirForm.id)
            sirAttachmentRepo.deleteFormAttachment(formTran.removeRecords.getOrElse(Seq.empty))
            val activity = Activity(UUID.randomUUID(), sirForm.id, "Add", Option(""), current, Option(identity.id))
            (formsRepo.updateMax("SIR", no) andThen activityRepo.insertActivity(activity))
          })
        })
      }
    }
  }

  def insertSirForm(sirForm: SirForm) = QueryTables.sirFormQuery += sirForm
  def updateSirForm(sirForm: SirForm) = (QueryTables.sirFormQuery.filter(q => q.id === sirForm.id)
    .map(m => (
      m.sirNo ::
      m.sirArea ::
      m.playerId ::
      m.sirMethodVerify ::
      m.sirCurrentRelationship ::
      m.sirAffiliationRelationship ::
      m.sirTypeSuspiciousActivities ::
      m.sirTranDateFrom ::
      m.sirTranDateTo ::
      m.ctrFormId ::
      m.ctrFormNo ::
      m.sirAmount ::
      m.sirSuspiciousIncidence ::
      m.sirWatchlist ::
      m.sirStaffId ::
      m.sirStaffDepartment ::
      m.sirDocumentNo ::
      m.submission ::
      m.status ::
      m.recievedDate ::
      m.recievedBy ::
      m.sirAffiliationRelationshipOthers ::
      m.sirCurrentRelationshipOthers ::
      m.sirTypeSuspiciousActivitiesOthers ::
      m.sirMethodVerifyOthers ::
      m.isCloneToSTR ::
      m.sirTransactionType ::
      m.playerWith ::
      m.modifiedDate ::
      m.modifiedBy :: HNil
    )).update((
    sirForm.sirNo ::
    sirForm.sirArea ::
    sirForm.playerId ::
    sirForm.sirMethodVerify ::
    sirForm.sirCurrentRelationship ::
    sirForm.sirAffiliationRelationship ::
    sirForm.sirTypeSuspiciousActivities ::
    sirForm.sirTranDateFrom ::
    sirForm.sirTranDateTo ::
    sirForm.ctrFormId ::
    sirForm.ctrFormNo ::
    sirForm.sirAmount ::
    sirForm.sirSuspiciousIncidence ::
    sirForm.sirWatchlist ::
    sirForm.sirStaffId ::
    sirForm.sirStaffDepartment ::
    sirForm.sirDocumentNo ::
    sirForm.submission ::
    sirForm.status ::
    sirForm.recievedDate ::
    sirForm.recievedBy ::
    sirForm.sirAffiliationRelationshipOthers ::
    sirForm.sirCurrentRelationshipOthers ::
    sirForm.sirTypeSuspiciousActivitiesOthers ::
    sirForm.sirMethodVerifyOthers ::
    sirForm.isCloneToSTR ::
    sirForm.sirTransactionType ::
    sirForm.playerWith ::
    sirForm.modifiedDate ::
    sirForm.modifiedBy :: HNil))
    )
}

