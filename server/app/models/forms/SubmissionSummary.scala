package models.forms

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._


final case class SubmissionSummary
(
  id: Option[UUID],
  submissionId: Option[String],
  playerId: Option[String],
  amount: Option[Float],
  noTrans: Option[Int],
  transType: Option[String],
  transCategory: Option[String],
  formIds: Option[String],
  reportIdentifier: Option[String],
)

object SubmissionSummary {
  implicit val format = Json.format[SubmissionSummary]
}

final class SubmissionSummaryTable (tag: Tag) extends Table[SubmissionSummary](tag, Some("form"), "SubmissionSummary") {
  def id = column[Option[UUID]]("id", O.PrimaryKey)
  def submissionId = column[Option[String]]("submissionId")
  def playerId = column[Option[String]]("playerId")
  def amount = column[Option[Float]]("amount")
  def noTrans = column[Option[Int]]("noTrans")
  def transType = column[Option[String]]("transType")
  def transCategory = column[Option[String]]("transCategory")
  def formIds = column[Option[String]]("formIds")
  def reportIdentifier = column[Option[String]]("reportIdentifier")

  def * = (id, submissionId, playerId, amount, noTrans, transType, transCategory, formIds, reportIdentifier) <> ((SubmissionSummary.apply _).tupled, SubmissionSummary.unapply)
}