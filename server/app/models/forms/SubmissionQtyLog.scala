package models.forms

import java.sql.Date
import java.util.UUID

import extensions.CustomColumnTypes._
import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._

case class SubmissionQtyLog (id: UUID, form: Option[String], lastQty: Option[Int], sentDate: Option[Date])

object SubmissionQtyLog {
  implicit val submissionQtyLogFormat = Json.format[SubmissionQtyLog]
}

final class SubmissionQtyLogTable (tag: Tag) extends Table[SubmissionQtyLog](tag, Some("form"), "submissionQtyLog") {
  def id = column[UUID]("id", O.PrimaryKey)
  def form = column[Option[String]]("form")
  def lastQty = column[Option[Int]]("lastQty")
  def sentDate = column[Option[Date]]("sentDate")
  def * = (id, form, lastQty, sentDate) <> ((SubmissionQtyLog.apply _).tupled, SubmissionQtyLog.unapply)
}
