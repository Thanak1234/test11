package models

import dto.{CtrFormDto, SirFormDto, StrFormDto, SubmissionDto}
import models.forms.Submission
import play.api.libs.json.Json

final case class SubmissionResource(records: Seq[Submission], totalRecords: Int)

object SubmissionResource {
  implicit val format = Json.format[SubmissionResource]
}

final case class CtrFormResource(records: Seq[CtrFormDto], totalRecords: Int)

object CtrFormResource {
  implicit val format = Json.format[CtrFormResource]
}

final case class SirFormResource(records: Seq[SirFormDto], totalRecords: Int)

object SirFormResource {
  implicit val format = Json.format[SirFormResource]
}

final case class StrFormResource(records: Seq[StrFormDto], totalRecords: Int)

object StrFormResource {
  implicit val format = Json.format[StrFormResource]
}

final case class PlayerResource(records: Seq[Player], totalRecords: Int)

object PlayerResource {
  implicit val format = Json.format[PlayerResource]
}


