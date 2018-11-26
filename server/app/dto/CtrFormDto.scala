package dto

import java.sql.Date
import java.util.UUID

import play.api.libs.json._
import extensions.JsonCustomType._
import extensions.SQLExtension._
import models.Player
import org.joda.time.DateTime
import slick.jdbc.GetResult

case class CtrFormDto(
                       id: Option[UUID],
                       formNo: Option[String],
                       typeOf: Option[String],
                       tran: Option[String],
                       location: Option[String],
                       area: Option[String],
                       amount: Option[Float],
                       playerId: Option[UUID],
                       playerName: Option[String],
                       playerCardType: Option[String],
                       driverLicense: Option[String],
                       gercCardNo: Option[String],
                       idCard: Option[String],
                       passport: Option[String],
                       others: Option[String],
                       noCard: Option[String],
                       dob: Option[Date],
                       tranDate: Option[DateTime],
                       submission: Option[Boolean],
                       void: Option[Boolean],
                       remark: Option[String],
                       status: Option[String],
                       staffId: Option[String],
                       docNo: Option[String],
                       playerWith: Option[String],
                       staffDepartment: Option[String]
)

object CtrFormDto {
  implicit val ctrformat = CtrFormFormatter
  implicit val getCtrFormDtoResult = GetResult(r => CtrFormDto(
    r.nextUUIDOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextFloatOption(),
    r.nextUUIDOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextDateOption(),
    r.nextDateTimeOption(),
    r.nextBooleanOption(),
    r.nextBooleanOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption()
  ))
}

object CtrFormFormatter extends Format[CtrFormDto] {
  override def reads(json: JsValue): JsResult[CtrFormDto] = JsSuccess(
    CtrFormDto(
      (json \ "id").asOpt[UUID],
      (json \ "formNo").asOpt[String],
      (json \ "typeOf").asOpt[String],
      (json \ "tran").asOpt[String],
      (json \ "location").asOpt[String],
      (json \ "area").asOpt[String],
      (json \ "amount").asOpt[Float],
      (json \ "playerId").asOpt[UUID],
      (json \ "playerName").asOpt[String],
      (json \ "playerCardType").asOpt[String],
      (json \ "driverLicense").asOpt[String],
      (json \ "gercCardNo").asOpt[String],
      (json \ "idCard").asOpt[String],
      (json \ "passport").asOpt[String],
      (json \ "others").asOpt[String],
      (json \ "noCard").asOpt[String],
      (json \ "dob").asOpt[Date],
      (json \ "tranDate").asOpt[DateTime],
      (json \ "submission").asOpt[Boolean],
      (json \ "void").asOpt[Boolean],
      (json \ "remark").asOpt[String],
      (json \ "status").asOpt[String],
      (json \ "staffId").asOpt[String],
      (json \ "docNo").asOpt[String],
      (json \ "playerWith").asOpt[String],
      (json \ "staffDepartment").asOpt[String]
    )
  )

  override def writes(o: CtrFormDto): JsValue = Json.obj(
    "id" -> o.id,
    "formNo" -> o.formNo,
    "typeOf" -> o.typeOf,
    "tran" -> o.tran,
    "location" -> o.location,
    "area" -> o.area,
    "amount" -> o.amount,
    "playerId" -> o.playerId,
    "playerName" -> o.playerName,
    "playerCardType" -> o.playerCardType,
    "driverLicense" -> o.driverLicense,
    "gercCardNo" -> o.gercCardNo,
    "idCard" -> o.idCard,
    "passport" -> o.passport,
    "others" -> o.others,
    "noCard" -> o.noCard,
    "dob" -> o.dob,
    "tranDate" -> o.tranDate,
    "submission" -> o.submission,
    "void" -> o.void,
    "remark" -> o.remark,
    "status" -> o.status,
    "staffId" -> o.staffId,
    "docNo" -> o.docNo,
    "playerWith" -> o.playerWith,
    "staffDepartment" -> o.staffDepartment
  )
}