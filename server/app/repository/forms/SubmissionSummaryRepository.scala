package repository.forms

import java.util.UUID

import dto.{ActivityLogDto, TransactionDto}
import extensions.CustomColumnTypes._
import javax.inject.{Inject, Singleton}
import models.{ComponentLookup, QueryTables}
import models.DbContext._
import models.forms.{Submission, SubmissionSummary}
import org.joda.time.DateTime
import repository.FormsRepository
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._
import utils.FormatUtil

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class SubmissionSummaryRepository @Inject() (formsRepository: FormsRepository, submissionQtyLogRepository: SubmissionQtyLogRepository) (implicit ec: ExecutionContext) {
  def findBySubmissionId(id: String) = QueryTables.submissionSummaryQuery.filter(q => q.submissionId === id).result
  def saves(trans: Seq[SubmissionSummary]) = QueryTables.submissionSummaryQuery ++= trans

  def updateReportIdentifier(submissionId: Option[String], formType: Option[String]) = {
    val record = Await.result(submissionQtyLogRepository.getLastQty(formType), Duration.Inf)
    record match {
      case Some(r) => {
        updateSumissionSummary(submissionId, formType, r.lastQty.getOrElse(0))
      }
      case None => {
        val qty = 0
        submissionQtyLogRepository.insert(formType, qty)
        updateSumissionSummary(submissionId, formType, qty)
      }
    }
  }

  def updateSumissionSummary(submissionId: Option[String], formType: Option[String], qty: Int) = {
    val records = Await.result(amlContext.run(QueryTables.submissionSummaryQuery.filter(p => p.reportIdentifier.isEmpty && p.submissionId === submissionId).result), Duration.Inf)
    var index = 1
    val updateSeq = DBIO.sequence(records.map(r => {
      val today = FormatUtil.dateFormat(DateTime.now(), "ddMMyy")
      val reportId = Option(s"310001%s%03d".format(today, qty + index))
      index = index + 1
      QueryTables.submissionSummaryQuery.filter(q => q.id === r.id).map(_.reportIdentifier).update(reportId)
    }))
    amlContext.run(updateSeq andThen submissionQtyLogRepository.updateLastQty(formType, qty + records.length))
  }
}
