package dto

import java.sql.Date
import java.util.UUID

import org.joda.time.DateTime

final case class SubmissionDto(
  id: Option[UUID],
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
  voidBy: Option[String]
)

