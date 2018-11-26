package models.forms

import java.util.UUID

import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class SubmissionTrans
(
  id: Option[UUID],
  submissionSummaryId: Option[UUID],
  formId: Option[String]
)

object SubmissionTrans {
  implicit val format = Json.format[SubmissionTrans]
}

final class SubmissionTransTable (tag: Tag) extends Table[SubmissionTrans](tag, Some("form"), "SubmissionTrans") {
  def id = column[Option[UUID]]("id", O.PrimaryKey)
  def submissionSummaryId = column[Option[UUID]]("submissionSummaryId")
  def formId = column[Option[String]]("formId")

  def * = (id, submissionSummaryId, formId) <> ((SubmissionTrans.apply _).tupled, SubmissionTrans.unapply)
}