package dto

import java.util.UUID

import models.FileAttachment
import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._

final case class CtrFormTran(
                              id : Option[UUID],
                              formNo : Option[String],
                              typeOf: Option[String],
                              tran : Option[String],
                              location : Option[String],
                              area : Option[String],
                              playerId : Option[UUID],
                              tranDate : Option[DateTime],
                              docNo : Option[String],
                              amount : Option[Float],
                              staffId : Option[String],
                              staffDepartment : Option[String],
                              remark : Option[String],
                              submission : Option[Boolean],
                              playerWith : Option[String],
                              comment: Option[String],
                              newRecords : Option[List[FileAttachment]],
                              removeRecords : Option[List[FileAttachment]]
                            )

object CtrFormTran {
  implicit val format = Json.format[CtrFormTran]
}