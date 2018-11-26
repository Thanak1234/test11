package dto

import java.sql.Date
import java.util.UUID

import play.api.libs.json._
import extensions.JsonCustomType._
import extensions.SQLExtension._
import models.Player
import org.joda.time.DateTime
import slick.jdbc.GetResult

case class SirFormDto(
                       id: Option[UUID],
                       sirNo: Option[String],
                       sirArea: Option[String],
                       playerId: Option[UUID],
                       sirMethodVerify: Option[String],
                       sirCurrentRelationship: Option[String],
                       sirAffiliationRelationship: Option[String],
                       sirTypeSuspiciousActivities: Option[String],
                       sirTranDateFrom: Option[DateTime],
                       sirTranDateTo: Option[DateTime],
                       ctrFormId: Option[UUID],
                       ctrFormNo: Option[String],
                       sirAmount: Option[Float],
                       sirSuspiciousIncidence: Option[String],
                       sirWatchlist: Option[Boolean],
                       sirStaffId: Option[String],
                       sirStaffDepartment: Option[String],
                       sirDocumentNo: Option[String],
                       submission: Option[Boolean],
                       status: Option[String],
                       recievedDate: Option[DateTime],
                       recievedBy: Option[String],
                       review: Option[Boolean],
                       sirAffiliationRelationshipOthers: Option[String],
                       sirCurrentRelationshipOthers: Option[String],
                       sirTypeSuspiciousActivitiesOthers: Option[String],
                       sirMethodVerifyOthers: Option[String],
                       isCloneToSTR: Option[Boolean],
                       sirTransactionType: Option[String],
                       playerWith: Option[String],
                       createdDate: Option[DateTime],
                       createdBy: Option[UUID],
                       modifiedDate: Option[DateTime],
                       modifiedBy: Option[UUID],
                       playerName: Option[String],
                       playerCardType: Option[String],
                       driverLicense: Option[String],
                       gercCardNo: Option[String],
                       idCard: Option[String],
                       passport: Option[String],
                       others: Option[String],
                       noCard: Option[String],
                       dob: Option[Date]
)

object SirFormDto {
  implicit val sirformat = SirFormFormatter
}

object SirFormFormatter extends Format[SirFormDto] {
  override def reads(json: JsValue): JsResult[SirFormDto] = JsSuccess(
    SirFormDto(
      (json \ "id").asOpt[UUID],
      (json \ "sirNo").asOpt[String],
      (json \ "sirArea").asOpt[String],
      (json \ "playerId").asOpt[UUID],
      (json \ "sirMethodVerify").asOpt[String],
      (json \ "sirCurrentRelationship").asOpt[String],
      (json \ "sirAffiliationRelationship").asOpt[String],
      (json \ "sirTypeSuspiciousActivities").asOpt[String],
      (json \ "sirTranDateFrom").asOpt[DateTime],
      (json \ "sirTranDateTo").asOpt[DateTime],
      (json \ "ctrFormId").asOpt[UUID],
      (json \ "ctrFormNo").asOpt[String],
      (json \ "sirAmount").asOpt[Float],
      (json \ "sirSuspiciousIncidence").asOpt[String],
      (json \ "sirWatchlist").asOpt[Boolean],
      (json \ "sirStaffId").asOpt[String],
      (json \ "sirStaffDepartment").asOpt[String],
      (json \ "sirDocumentNo").asOpt[String],
      (json \ "submission").asOpt[Boolean],
      (json \ "status").asOpt[String],
      (json \ "recievedDate").asOpt[DateTime],
      (json \ "recievedBy").asOpt[String],
      (json \ "review").asOpt[Boolean],
      (json \ "sirAffiliationRelationshipOthers").asOpt[String],
      (json \ "sirCurrentRelationshipOthers").asOpt[String],
      (json \ "sirTypeSuspiciousActivitiesOthers").asOpt[String],
      (json \ "sirMethodVerifyOthers").asOpt[String],
      (json \ "isCloneToSTR").asOpt[Boolean],
      (json \ "sirTransactionType").asOpt[String],
      (json \ "playerWith").asOpt[String],
      (json \ "createdDate").asOpt[DateTime],
      (json \ "createdBy").asOpt[UUID],
      (json \ "modifiedDate").asOpt[DateTime],
      (json \ "modifiedBy").asOpt[UUID],
      (json \ "playerName").asOpt[String],
      (json \ "playerCardType").asOpt[String],
      (json \ "driverLicense").asOpt[String],
      (json \ "gercCardNo").asOpt[String],
      (json \ "idCard").asOpt[String],
      (json \ "passport").asOpt[String],
      (json \ "others").asOpt[String],
      (json \ "noCard").asOpt[String],
      (json \ "dob").asOpt[Date]
    )
  )

  override def writes(o: SirFormDto): JsValue = Json.obj(
  "id" -> o.id,
    "sirNo" -> o.sirNo,
    "sirArea" -> o.sirArea,
    "playerId" -> o.playerId,
    "sirMethodVerify" -> o.sirMethodVerify,
    "sirCurrentRelationship" -> o.sirCurrentRelationship,
    "sirAffiliationRelationship" -> o.sirAffiliationRelationship,
    "sirTypeSuspiciousActivities" -> o.sirTypeSuspiciousActivities,
    "sirTranDateFrom" -> o.sirTranDateFrom,
    "sirTranDateTo" -> o.sirTranDateTo,
    "ctrFormId" -> o.ctrFormId,
    "ctrFormNo" -> o.ctrFormNo,
    "sirAmount" -> o.sirAmount,
    "sirSuspiciousIncidence" -> o.sirSuspiciousIncidence,
    "sirWatchlist" -> o.sirWatchlist,
    "sirStaffId" -> o.sirStaffId,
    "sirStaffDepartment" -> o.sirStaffDepartment,
    "sirDocumentNo" -> o.sirDocumentNo,
    "submission" -> o.submission,
    "status" -> o.status,
    "recievedDate" -> o.recievedDate,
    "recievedBy" -> o.recievedBy,
    "review" -> o.review,
    "sirAffiliationRelationshipOthers" -> o.sirAffiliationRelationshipOthers,
    "sirCurrentRelationshipOthers" -> o.sirCurrentRelationshipOthers,
    "sirTypeSuspiciousActivitiesOthers" -> o.sirTypeSuspiciousActivitiesOthers,
    "sirMethodVerifyOthers" -> o.sirMethodVerifyOthers,
    "isCloneToSTR" -> o.isCloneToSTR,
    "sirTransactionType" -> o.sirTransactionType,
    "playerWith" -> o.playerWith,
    "createdDate" -> o.createdDate,
    "createdBy" -> o.createdBy,
    "modifiedDate" -> o.modifiedDate,
    "modifiedBy" -> o.modifiedBy,
    "playerId" -> o.playerId,
    "playerName" -> o.playerName,
    "playerCardType" -> o.playerCardType,
    "driverLicense" -> o.driverLicense,
    "gercCardNo" -> o.gercCardNo,
    "idCard" -> o.idCard,
    "passport" -> o.passport,
    "others" -> o.others,
    "noCard" -> o.noCard,
    "dob" -> o.dob
  )
}