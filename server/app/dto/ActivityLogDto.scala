package dto

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._

final case class ActivityLogDto(id: UUID, formId: Option[UUID], action: String, comment: Option[String], createdDate: DateTime, createdBy: Option[String])

object ActivityLogDto {
  implicit val format = Json.format[ActivityLogDto]
}