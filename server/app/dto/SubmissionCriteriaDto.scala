package dto

import java.sql.Date

import play.api.libs.json.Json

final case class SubmissionCriteriaDto(formType: Option[String], transDate: Option[Date], multiAmount: Option[Float], singleAmount: Option[Float], exclusiveCountry: Option[Seq[String]])

object SubmissionCriteriaDto {
  implicit val format = Json.format[SubmissionCriteriaDto]
}