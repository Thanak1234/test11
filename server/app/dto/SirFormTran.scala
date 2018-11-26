package dto

import java.sql.Date
import java.util.UUID

import models.FileAttachment
import models.forms.FormAttachment
import org.joda.time.DateTime
import play.api.libs.json._
import extensions.JsonCustomType._

final case class SirFormTran(
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
                              comment: Option[String],
                              newRecords : Option[Seq[FormAttachment]],
                              removeRecords : Option[Seq[FormAttachment]]
                            )

object SirFormTran {
  implicit val sirFormFormat = SirFormTranFormatter
}


object SirFormTranFormatter extends Format[SirFormTran] {
  override def reads(json: JsValue): JsResult[SirFormTran] = JsSuccess(
    SirFormTran(
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
      (json \ "comment").asOpt[String],
      (json \ "newRecords").asOpt[Seq[FormAttachment]],
      (json \ "removeRecords").asOpt[Seq[FormAttachment]],
    )
  )

  override def writes(o: SirFormTran): JsValue = Json.obj(
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
    "comment" -> o.comment,
    "newRecords" -> o.newRecords ,
    "removeRecords" -> o.removeRecords
  )
}
