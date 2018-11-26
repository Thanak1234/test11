package dto

import java.sql.Date
import java.util.UUID

import models.FileAttachment
import models.forms.FormAttachment
import org.joda.time.DateTime
import play.api.libs.json._
import extensions.JsonCustomType._

final case class StrFormTran(
                              id: Option[UUID],
                              strNo: Option[String],
                              sirNo: Option[String],
                              strArea: Option[String],
                              playerId: Option[UUID],
                              strMethodVerify: Option[String],
                              strCurrentRelationship: Option[String],
                              strAffiliationRelationship: Option[String],
                              strTypeSuspiciousActivities: Option[String],
                              strTranDateFrom: Option[DateTime],
                              strTranDateTo: Option[DateTime],
                              ctrFormId: Option[UUID],
                              ctrFormNo: Option[String],
                              strAmount: Option[Float],
                              strSuspiciousIncidence: Option[String],
                              strWatchlist: Option[Boolean],
                              strStaffId: Option[String],
                              strStaffDepartment: Option[String],
                              strDocumentNo: Option[String],
                              submission: Option[Boolean],
                              status: Option[String],
                              recievedDate: Option[DateTime],
                              recievedBy: Option[String],
                              review: Option[Boolean],
                              strAffiliationRelationshipOthers: Option[String],
                              strCurrentRelationshipOthers: Option[String],
                              strTypeSuspiciousActivitiesOthers: Option[String],
                              strMethodVerifyOthers: Option[String],
                              strTransactionType: Option[String],
                              playerWith: Option[String],
                              comment: Option[String],
                              newRecords : Option[Seq[FormAttachment]],
                              removeRecords : Option[Seq[FormAttachment]]
                            )

object StrFormTran {
  implicit val strFormFormat = StrFormTranFormatter
}


object StrFormTranFormatter extends Format[StrFormTran] {
  override def reads(json: JsValue): JsResult[StrFormTran] = JsSuccess(
    StrFormTran(
      (json \ "id").asOpt[UUID],
      (json \ "strNo").asOpt[String],
      (json \ "sirNo").asOpt[String],
      (json \ "strArea").asOpt[String],
      (json \ "playerId").asOpt[UUID],
      (json \ "strMethodVerify").asOpt[String],
      (json \ "strCurrentRelationship").asOpt[String],
      (json \ "strAffiliationRelationship").asOpt[String],
      (json \ "strTypeSuspiciousActivities").asOpt[String],
      (json \ "strTranDateFrom").asOpt[DateTime],
      (json \ "strTranDateTo").asOpt[DateTime],
      (json \ "ctrFormId").asOpt[UUID],
      (json \ "ctrFormNo").asOpt[String],
      (json \ "strAmount").asOpt[Float],
      (json \ "strSuspiciousIncidence").asOpt[String],
      (json \ "strWatchlist").asOpt[Boolean],
      (json \ "strStaffId").asOpt[String],
      (json \ "strStaffDepartment").asOpt[String],
      (json \ "strDocumentNo").asOpt[String],
      (json \ "submission").asOpt[Boolean],
      (json \ "status").asOpt[String],
      (json \ "recievedDate").asOpt[DateTime],
      (json \ "recievedBy").asOpt[String],
      (json \ "review").asOpt[Boolean],
      (json \ "strAffiliationRelationshipOthers").asOpt[String],
      (json \ "strCurrentRelationshipOthers").asOpt[String],
      (json \ "strTypeSuspiciousActivitiesOthers").asOpt[String],
      (json \ "strMethodVerifyOthers").asOpt[String],
      (json \ "strTransactionType").asOpt[String],
      (json \ "playerWith").asOpt[String],
      (json \ "comment").asOpt[String],
      (json \ "newRecords").asOpt[Seq[FormAttachment]],
      (json \ "removeRecords").asOpt[Seq[FormAttachment]],
    )
  )

  override def writes(o: StrFormTran): JsValue = Json.obj(
    "id" -> o.id,
    "strNo" -> o.strNo,
    "sirNo" -> o.sirNo,
    "strArea" -> o.strArea,
    "playerId" -> o.playerId,
    "strMethodVerify" -> o.strMethodVerify,
    "strCurrentRelationship" -> o.strCurrentRelationship,
    "strAffiliationRelationship" -> o.strAffiliationRelationship,
    "strTypeSuspiciousActivities" -> o.strTypeSuspiciousActivities,
    "strTranDateFrom" -> o.strTranDateFrom,
    "strTranDateTo" -> o.strTranDateTo,
    "ctrFormId" -> o.ctrFormId,
    "ctrFormNo" -> o.ctrFormNo,
    "strAmount" -> o.strAmount,
    "strSuspiciousIncidence" -> o.strSuspiciousIncidence,
    "strWatchlist" -> o.strWatchlist,
    "strStaffId" -> o.strStaffId,
    "strStaffDepartment" -> o.strStaffDepartment,
    "strDocumentNo" -> o.strDocumentNo,
    "submission" -> o.submission,
    "status" -> o.status,
    "recievedDate" -> o.recievedDate,
    "recievedBy" -> o.recievedBy,
    "review" -> o.review,
    "strAffiliationRelationshipOthers" -> o.strAffiliationRelationshipOthers,
    "strCurrentRelationshipOthers" -> o.strCurrentRelationshipOthers,
    "strTypeSuspiciousActivitiesOthers" -> o.strTypeSuspiciousActivitiesOthers,
    "strMethodVerifyOthers" -> o.strMethodVerifyOthers,
    "strTransactionType" -> o.strTransactionType,
    "playerWith" -> o.playerWith,
    "comment" -> o.comment,
    "newRecords" -> o.newRecords ,
    "removeRecords" -> o.removeRecords
  )
}
