package repository.forms

import java.sql.Date
import java.util.UUID

import auth.models.IdentityUser
import dto.{CtrFormDto, CtrFormTran, TransactionDto}
import extensions.CustomColumnTypes._
import extensions.JsonCustomType.dateTimeFormat
import javax.inject.{Inject, Singleton}
import models.DbContext._
import models.forms.{CtrForm, CtrFormTable}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import repository.{ActivityRepository, FileAttachmentRepository, FormsRepository}
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._
import extensions.DynamicSortBySupport._
import models.{forms, _}
import utils.{Filter, FilterSort, Sort}

import scala.concurrent.ExecutionContext

@Singleton
class CtrFormRepository @Inject()(
                                 formsRepo: FormsRepository,
                                 activityRepo: ActivityRepository,
                                 fileAttachmentRepo: FileAttachmentRepository
                                 )(implicit ec: ExecutionContext) {

  def findCtrFormById(id: String) = amlContext.run {
    QueryTables.ctrFormQuery.join(QueryTables.playerQuery).on({case (form, player)=> form.playerId === player.id})
    .filter(q => q._1.id === UUID.fromString(id)).map({ case (form, player) => (
      form.id ::
        form.no ::
        form.typeOf ::
        form.tran ::
        form.location ::
        form.area ::
        form.amount ::
        form.playerId ::
        player.playerName ::
        player.playerCardType ::
        player.driverLicense ::
        player.gercCardNo ::
        player.idCard ::
        player.passport ::
        player.others ::
        player.noCard ::
        player.dob ::
        form.tranDate ::
        form.submission ::
        form.submission ::
        form.remark ::
        form.status ::
        form.staffId ::
        form.docNo ::
        form.playerWith ::
        form.staffDepartment :: HNil)
      .mapTo[CtrFormDto]
    }).result.headOption
  }

  def list(filterSort: FilterSort) = amlContext run {

    val queryText = filterSort.query.getOrElse("")
    val fieldFilters = if (filterSort.filter.isEmpty) Seq.empty  else Json.parse(filterSort.filter.get).validate[Seq[Filter]].get
    val orderFields = if (filterSort.sort.isEmpty) Seq.empty  else Json.parse(filterSort.sort.get).validate[Seq[Sort]].get

    val filterQuery = QueryTables.ctrFormQuery.join(QueryTables.playerQuery).on({case (form, player)=> form.playerId === player.id})
      .filter(q => buildColumnFilters(fieldFilters, q._1, q._2) && buildSearchQueryFilters(Option(s"%$queryText%"), q._1, q._2))

    val sortQuery = buildSort(filterQuery, orderFields)

    val selectQuery = sortQuery.map({ case (form, player) => (
        form.id ::
          form.no ::
          form.typeOf ::
          form.tran ::
          form.location ::
          form.area ::
          form.amount ::
          form.playerId ::
          player.playerName ::
          player.playerCardType ::
          player.driverLicense ::
          player.gercCardNo ::
          player.idCard ::
          player.passport ::
          player.others ::
          player.noCard ::
          player.dob ::
          form.tranDate ::
          form.submission ::
          form.submission ::
          form.remark ::
          form.status ::
          form.staffId ::
          form.docNo ::
          form.playerWith ::
          form.staffDepartment :: HNil)
        .mapTo[CtrFormDto]
      })

    for {
      totalRecords <- selectQuery.length.result
      records <- selectQuery
        .drop(filterSort.start.getOrElse(0))
        .take(filterSort.limit.getOrElse(0))
        .result
    } yield CtrFormResource(records, totalRecords)
  }

  def buildSort(query: Query[(CtrFormTable, PlayerTable), (CtrFormTable#TableElementType, PlayerTable#TableElementType), Seq], orderFields: Seq[Sort]) = {
    orderFields.isEmpty match {
      case true => query
      case false => {
        val orderField = orderFields.headOption
        orderField match {
          case Some(field) => {
            val property = field.property
            val direction = field.direction
            query.sortBy(s => {
              property match {
                case Some("formNo") if direction == Some("ASC")  => s._1.no.asc
                case Some("formNo") if direction == Some("DESC")  => s._1.no.desc
                case Some("tranDate") if direction == Some("ASC")  => s._1.tranDate.asc
                case Some("tranDate") if direction == Some("DESC")  => s._1.tranDate.desc
                case _ => s._1.no.desc
              }
            })
          }
          case None => query
        }
      }
    }
  }

  def buildColumnFilters(filters: Seq[Filter], form: CtrFormTable, player: PlayerTable) = {
    val dateFormat = "MM/dd/yyyy"
    val columnsFilter = filters.map(c => {
      if(c.operator == Some("lt"))
        c.value.map(form.tranDate <= DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
      else if(c.operator == Some("gt"))
        c.value.map(form.tranDate >= DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
      else
        c.value.map(form.tranDate === DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
    }).toList

    columnsFilter
      .collect({case Some(c) => c})
      .reduceLeftOption(_ && _)
      .getOrElse(Some(true): Rep[Option[Boolean]])
  }

  def buildSearchQueryFilters(searchQuery: Option[String], form: CtrFormTable, player: PlayerTable) = List(
    searchQuery.map(form.no like _),
    searchQuery.map(form.typeOf like _),
    searchQuery.map(form.location like _),
    searchQuery.map(player.playerName like _),
    searchQuery.map(player.passport like _)
  ).collect({ case Some(criteria) => criteria })
    .reduceLeftOption(_ || _)
    .getOrElse(Some(true): Rep[Option[Boolean]])

  def save(ctrFormTra: CtrFormTran, identity: IdentityUser) = amlContext.run {
    ctrFormTra.id match {
      case Some(id) => {
        val ctrForm = CtrForm(ctrFormTra.id, ctrFormTra.formNo, ctrFormTra.typeOf, ctrFormTra.tran, ctrFormTra.location,
          ctrFormTra.area, ctrFormTra.playerId, ctrFormTra.tranDate, ctrFormTra.docNo, ctrFormTra.amount, ctrFormTra.staffId,
          ctrFormTra.staffDepartment, ctrFormTra.remark, None, None, Option(new DateTime()), Option(identity.id),
          ctrFormTra.submission, Option("Active"), ctrFormTra.playerWith)
        updateCtrForm(ctrForm).flatMap(r => {
          fileAttachmentRepo.updateFileAttachment(ctrFormTra.newRecords.getOrElse(List.empty), ctrForm.id)
          fileAttachmentRepo.deleteFileAttachment(ctrFormTra.removeRecords.getOrElse(List.empty))
          val activity = Activity(UUID.randomUUID(), ctrForm.id, "Edit", ctrFormTra.comment, new DateTime(), Option(identity.id))
          activityRepo.insertActivity(activity)
        })
      }
      case None => {
        formsRepo.getFormMax("CTR").flatMap(max => {
          val no = max + 1
          val formNo = Some(s"CTR-%06d".format(no))
          val ctrForm = CtrForm(Option(UUID.randomUUID()), formNo, ctrFormTra.typeOf, ctrFormTra.tran, ctrFormTra.location, ctrFormTra.area, ctrFormTra.playerId, ctrFormTra.tranDate, ctrFormTra.docNo, ctrFormTra.amount, ctrFormTra.staffId, ctrFormTra.staffDepartment, ctrFormTra.remark, Option(new DateTime()), Option(identity.id), None, None, ctrFormTra.submission, Option("Active"), ctrFormTra.playerWith)
          insertCtrForm(ctrForm).flatMap(f => {
            fileAttachmentRepo.updateFileAttachment(ctrFormTra.newRecords.getOrElse(List.empty), ctrForm.id)
            fileAttachmentRepo.deleteFileAttachment(ctrFormTra.removeRecords.getOrElse(List.empty))
            val activity = Activity(UUID.randomUUID(), ctrForm.id, "Add", Option(""), new DateTime(), Option(identity.id))
            (formsRepo.updateMax("CTR", no) andThen activityRepo.insertActivity(activity))
          })

        })
      }
    }
  }

  def insertCtrForm(ctrForm: CtrForm) = (QueryTables.ctrFormQuery += ctrForm)
  def updateCtrForm(ctrForm: CtrForm) = (QueryTables.ctrFormQuery.filter(q => q.id === ctrForm.id)
    .map(r => (r.typeOf, r.tran, r.location, r.area, r.playerId, r.tranDate, r.docNo, r.amount, r.staffId, r.staffDepartment, r.remark, r.modifiedBy, r.modifiedDate))
    .update((ctrForm.typeOf, ctrForm.tran, ctrForm.location, ctrForm.area, ctrForm.playerId, ctrForm.tranDate, ctrForm.docNo, ctrForm.amount, ctrForm.staffId,
      ctrForm.staffDepartment, ctrForm.remark, ctrForm.modifiedBy, ctrForm.modifiedDate)))

  def singleTransaction(dateFrom: Date, excludeCountry: String, amount: Float) = amlContext.run {
    sql"EXECUTE Form.spCTRMultiOrSingle ${dateFrom}, ${excludeCountry}, ${amount}, 0, 1".as[TransactionDto]
  }

  def multiTransaction(dateFrom: Date, excludeCountry: String, amount: Float) = amlContext.run {
    sql"EXECUTE Form.spCTRMultiOrSingle ${dateFrom}, ${excludeCountry}, ${amount}, 1, 0".as[TransactionDto]
  }

  def CTRPreview(formIds: String) = amlContext.run {
    sql"EXECUTE Form.spCTRPreview $formIds".as[CtrFormDto]
  }
}




