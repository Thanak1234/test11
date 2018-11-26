package models.forms

import java.util.UUID

import org.joda.time.DateTime
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

case class StrForm (
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
                     createdDate: Option[DateTime],
                     createdBy: Option[UUID],
                     modifiedDate: Option[DateTime],
                     modifiedBy: Option[UUID]
                   )


final class StrFormTable (tag: Tag) extends Table[StrForm](tag, Some("form"), "strForm") {

  def id = column[Option[UUID]]("id", O.PrimaryKey)
  def strNo = column[Option[String]]("strNo")
  def sirNo = column[Option[String]]("sirNo")
  def strArea = column[Option[String]]("strArea")
  def playerId = column[Option[UUID]]("playerId")
  def strMethodVerify = column[Option[String]]("strMethodVerify")
  def strCurrentRelationship = column[Option[String]]("strCurrentRelationship")
  def strAffiliationRelationship = column[Option[String]]("strAffiliationRelationship")
  def strTypeSuspiciousActivities = column[Option[String]]("strTypeSuspiciousActivities")
  def strTranDateFrom = column[Option[DateTime]]("strTranDateFrom")
  def strTranDateTo = column[Option[DateTime]]("strTranDateTo")
  def ctrFormId = column[Option[UUID]]("ctrFormId")
  def ctrFormNo = column[Option[String]]("ctrFormNo")
  def strAmount = column[Option[Float]]("strAmount")
  def strSuspiciousIncidence = column[Option[String]]("strSuspiciousIncidence")
  def strWatchlist = column[Option[Boolean]]("strWatchlist")
  def strStaffId = column[Option[String]]("strStaffId")
  def strStaffDepartment = column[Option[String]]("strStaffDepartment")
  def strDocumentNo = column[Option[String]]("strDocumentNo")
  def submission = column[Option[Boolean]]("submission")
  def status = column[Option[String]]("status")
  def recievedDate = column[Option[DateTime]]("recievedDate")
  def recievedBy = column[Option[String]]("recievedBy")
  def review = column[Option[Boolean]]("review")
  def strAffiliationRelationshipOthers = column[Option[String]]("strAffiliationRelationshipOthers")
  def strCurrentRelationshipOthers = column[Option[String]]("strCurrentRelationshipOthers")
  def strTypeSuspiciousActivitiesOthers = column[Option[String]]("strTypeSuspiciousActivitiesOthers")
  def strMethodVerifyOthers = column[Option[String]]("strMethodVerifyOthers")
  def strTransactionType = column[Option[String]]("strTransactionType")
  def playerWith = column[Option[String]]("playerWith")
  def createdDate = column[Option[DateTime]]("createdDate")
  def createdBy = column[Option[UUID]]("createdBy")
  def modifiedDate = column[Option[DateTime]]("modifiedDate")
  def modifiedBy = column[Option[UUID]]("modifiedBy")

  def * = (
    id ::
      strNo ::
      sirNo ::
      strArea ::
      playerId ::
      strMethodVerify ::
      strCurrentRelationship ::
      strAffiliationRelationship ::
      strTypeSuspiciousActivities ::
      strTranDateFrom ::
      strTranDateTo ::
      ctrFormId ::
      ctrFormNo ::
      strAmount ::
      strSuspiciousIncidence ::
      strWatchlist ::
      strStaffId ::
      strStaffDepartment ::
      strDocumentNo ::
      submission ::
      status ::
      recievedDate ::
      recievedBy ::
      review ::
      strAffiliationRelationshipOthers ::
      strCurrentRelationshipOthers ::
      strTypeSuspiciousActivitiesOthers ::
      strMethodVerifyOthers ::
      strTransactionType ::
      playerWith ::
      createdDate ::
      createdBy ::
      modifiedDate ::
      modifiedBy :: HNil
    ).mapTo[StrForm]
}
