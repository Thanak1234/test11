package models.forms

import java.sql.Date
import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._
import extensions.JsonCustomType._

final case class SubmissionLog
(
  id: Option[UUID],
  submissionId: Option[UUID],
  action: Option[String],
  actionDate: Option[DateTime],
  actionBy: Option[UUID],
  status: Option[String],
  description: Option[String]
)

object SubmissionLog {
  implicit val format = Json.format[SubmissionLog]
}

final class SubmissionLogTable (tag: Tag) extends Table[SubmissionLog](tag, Some("form"), "SubmissionLog") {
  def id = column[Option[UUID]]("id", O.PrimaryKey)
  def submissionId = column[Option[UUID]]("submissionId")
  def action = column[Option[String]]("action")
  def actionDate = column[Option[DateTime]]("actionDate")
  def actionBy = column[Option[UUID]]("actionBy")
  def status = column[Option[String]]("status")
  def description = column[Option[String]]("description")

  def * = (id, submissionId, action, actionDate, actionBy, status, description) <> ((SubmissionLog.apply _).tupled, SubmissionLog.unapply)
}
