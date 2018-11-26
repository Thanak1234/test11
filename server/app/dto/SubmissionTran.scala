package dto

import play.api.libs.json.Json

final case class SubmissionTran(criteria: SubmissionCriteriaDto, transactions: Seq[TransactionDto])

object SubmissionTran {
  implicit val format = Json.format[SubmissionTran]
}