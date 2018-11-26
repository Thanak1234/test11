package models.forms

import java.sql.Date
import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._
import extensions.JsonCustomType._

final case class Submission
(
  id: Option[String],
  submissionCode: Option[String],
  formType: Option[String],
  transDate: Option[Date],
  multiAmount: Option[Float],
  singleAmount: Option[Float],
  actionType: Option[String],
  status: Option[String],
  saveDate: Option[DateTime],
  saveBy: Option[String],
  sentDate: Option[DateTime],
  sentBy: Option[String],
  comment: Option[String],
  voidDate: Option[DateTime],
  voidBy: Option[String],
)

object Submission {
  implicit val format = Json.format[Submission]
}

final class SubmissionTable (tag: Tag) extends Table[Submission](tag, Some("form"), "submission") {
  def id = column[Option[String]]("id", O.PrimaryKey)
  def submissionCode = column[Option[String]]("submissionCode")
  def formType = column[Option[String]]("formType")
  def transDate = column[Option[Date]]("transDate")
  def multiAmount = column[Option[Float]]("multiAmount")
  def singleAmount = column[Option[Float]]("singleAmount")
  def actionType = column[Option[String]]("actionType")
  def status = column[Option[String]]("status")
  def saveDate = column[Option[DateTime]]("saveDate")
  def saveBy = column[Option[String]]("saveBy")
  def sentDate = column[Option[DateTime]]("sentDate")
  def sentBy = column[Option[String]]("sentBy")
  def comment = column[Option[String]]("comment")
  def voidDate = column[Option[DateTime]]("voidDate")
  def voidBy = column[Option[String]]("voidBy")

  def * = (id, submissionCode, formType, transDate, multiAmount, singleAmount, actionType, status,
  saveDate, saveBy, sentDate, sentBy, comment, voidDate, voidBy) <> ((Submission.apply _).tupled, Submission.unapply)
}

