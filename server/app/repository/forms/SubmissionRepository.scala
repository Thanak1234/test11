package repository.forms

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.UUID

import auth.models.IdentityUser
import dto.{SubmissionCriteriaDto, SubmissionDto, SubmissionXMLDto, TransactionDto}
import extensions.CustomColumnTypes._
import javax.inject.{Inject, Singleton}
import models.DbContext._
import models.forms._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import repository.FormsRepository
import slick.jdbc.SQLServerProfile.api._
import models._
import utils.{Filter, FilterSort, Sort}

import scala.concurrent.ExecutionContext

@Singleton
class SubmissionRepository @Inject() (
  formsRepository: FormsRepository,
  submissionSummaryRepository: SubmissionSummaryRepository,
  submissionTransRepository: SubmissionTransRepository,
  submissionLogRepository: SubmissionLogRepository
) (implicit ec: ExecutionContext) {

  def list(filterSort: FilterSort) = amlContext.run {
    val queryText = filterSort.query.getOrElse("")
    val fieldFilters = if (filterSort.filter.isEmpty) Seq.empty  else Json.parse(filterSort.filter.get).validate[Seq[Filter]].get
    val orderFields = if (filterSort.sort.isEmpty) Seq.empty  else Json.parse(filterSort.sort.get).validate[Seq[Sort]].get

    val filterQuery = QueryTables.submissionQuery
      .filter(q => buildColumnFilters(fieldFilters, q) && buildSearchQueryFilters(Option(s"%$queryText%"), q))
    val sortBy = filterQuery.sortBy(s => s.submissionCode.desc)

    for {
      totalRecords <- filterQuery.length.result
      records <- filterQuery
        .drop(filterSort.start.getOrElse(0))
        .take(filterSort.limit.getOrElse(0))
        .result
    } yield SubmissionResource(records, totalRecords)
  }

  def buildColumnFilters(filters: Seq[Filter], submission: SubmissionTable) = {
    val columnsFilter = filters.map(c => {
      val dateFormat = "MM/dd/yyyy"
      val d = new Date(DateTime.parse(c.value.get, DateTimeFormat.forPattern(dateFormat)).toDate.getTime)
      if(c.operator == Some("lt"))
        Option(submission.transDate <= d)
      else if(c.operator == Some("gt"))
        Option(submission.transDate >= d)
      else
        Option(submission.transDate === d)
    }).toList

    columnsFilter
      .collect({case Some(c) => c})
      .reduceLeftOption(_ && _)
      .getOrElse(Some(true): Rep[Option[Boolean]])
  }

  def buildSearchQueryFilters(searchQuery: Option[String], submission: SubmissionTable) = List(
    searchQuery.map(submission.submissionCode like _),
    searchQuery.map(submission.formType like _),
  ).collect({ case Some(criteria) => criteria })
    .reduceLeftOption(_ || _)
    .getOrElse(Some(true): Rep[Option[Boolean]])

  def save(criteria: SubmissionCriteriaDto, transactions: Seq[TransactionDto], identity: IdentityUser) = amlContext.run {
    formsRepository.getFormMax("SUBMISSION").flatMap(max => {
      val id = UUID.randomUUID()
      val no = max + 1
      val formNo = Option(s"FIU-%06d".format(no))
      val submission = Submission(
        Option(id.toString),
        formNo,
        criteria.formType,
        criteria.transDate,
        criteria.multiAmount,
        criteria.singleAmount,
        Option("Save"),
        Option("Complete"),
        Option(new DateTime()),
        identity.basicInfo.fullName,
        None,
        None,
        None,
        None,
        None
      )

      val summaries = buildSummary(Option(id.toString), transactions)
      val summaryTrans = buildSubmissionTrans(summaries)
      val log = SubmissionLog(Option(UUID.randomUUID()), Option(id), Option("Save"), Option(new DateTime()), Option(identity.id), Option("Active"), Option("Submission save completed..."))

      (
        saveSubmission(submission) andThen
          submissionSummaryRepository.saves(summaries) andThen
          submissionTransRepository.saves(summaryTrans) andThen
          formsRepository.updateMax("SUBMISSION", no) andThen
          submissionLogRepository.save(log)
        ).transactionally
    })
  }

  def buildSummary(submissionId: Option[String], transactions: Seq[TransactionDto]) = transactions.map(m => {
    val summaryId = Option(UUID.randomUUID())
    SubmissionSummary(Option(UUID.randomUUID()), submissionId, m.playerId, m.totalAmount, m.noTrans, m.transType, m.categoryName, m.frmIds, None)
  })

  def saveSubmission(submission: Submission) = QueryTables.submissionQuery += submission

  def buildSubmissionTrans(summaries: Seq[SubmissionSummary]) = {
    var trans: Seq[SubmissionTrans] = Seq()
    summaries.foreach(m => {
      val ids = m.formIds.get.split(",").toSeq
      ids.foreach(n => {
        trans = trans :+ SubmissionTrans(Option(UUID.randomUUID()), m.id, Option(n))
      })
    })
    trans
  }

  def getCtrSubmissionXML(id: String) = amlContext.run(sql"EXECUTE Form.spCTRSubmissionXML ${id}".as[SubmissionXMLDto])

//  def updateSentBy(submissionId: String) = amlContext.run(QueryTables.submissionQuery.filter(p => p.id === submissionId).map(m => (m.sentDate, m.sentBy).))
}
