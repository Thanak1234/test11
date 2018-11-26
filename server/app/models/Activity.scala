package models

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

case class Activity (id: UUID, formId: Option[UUID], action: String, comment: Option[String], createdDate: DateTime, createdBy: Option[UUID])

object Activity {
  implicit val activityFormat = Json.format[Activity]
}

final class ActivityTable (tag: Tag) extends Table[Activity](tag, Some("form"), "activityLog") {
  def id = column[UUID]("id", O.PrimaryKey)
  def formId = column[Option[UUID]]("formId")
  def action = column[String]("action")
  def comment = column[Option[String]]("comment")
  def createdDate = column[DateTime]("createdDate")
  def createdBy = column[Option[UUID]]("createdBy")
  def * = (id, formId, action, comment, createdDate, createdBy) <> ((Activity.apply _).tupled, Activity.unapply)
}
