package models.forms

import java.util.UUID

import org.joda.time.DateTime
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

case class SirForm (
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
                     modifiedBy: Option[UUID]
                   )


final class SirFormTable (tag: Tag) extends Table[SirForm](tag, Some("form"), "sirForm") {

  def id = column[Option[UUID]]("id", O.PrimaryKey)
  def sirNo = column[Option[String]]("sirNo")
  def sirArea = column[Option[String]]("sirArea")
  def playerId = column[Option[UUID]]("playerId")
  def sirMethodVerify = column[Option[String]]("sirMethodVerify")
  def sirCurrentRelationship = column[Option[String]]("sirCurrentRelationship")
  def sirAffiliationRelationship = column[Option[String]]("sirAffiliationRelationship")
  def sirTypeSuspiciousActivities = column[Option[String]]("sirTypeSuspiciousActivities")
  def sirTranDateFrom = column[Option[DateTime]]("sirTranDateFrom")
  def sirTranDateTo = column[Option[DateTime]]("sirTranDateTo")
  def ctrFormId = column[Option[UUID]]("ctrFormId")
  def ctrFormNo = column[Option[String]]("ctrFormNo")
  def sirAmount = column[Option[Float]]("sirAmount")
  def sirSuspiciousIncidence = column[Option[String]]("sirSuspiciousIncidence")
  def sirWatchlist = column[Option[Boolean]]("sirWatchlist")
  def sirStaffId = column[Option[String]]("sirStaffId")
  def sirStaffDepartment = column[Option[String]]("sirStaffDepartment")
  def sirDocumentNo = column[Option[String]]("sirDocumentNo")
  def submission = column[Option[Boolean]]("submission")
  def status = column[Option[String]]("status")
  def recievedDate = column[Option[DateTime]]("recievedDate")
  def recievedBy = column[Option[String]]("recievedBy")
  def review = column[Option[Boolean]]("review")
  def sirAffiliationRelationshipOthers = column[Option[String]]("sirAffiliationRelationshipOthers")
  def sirCurrentRelationshipOthers = column[Option[String]]("sirCurrentRelationshipOthers")
  def sirTypeSuspiciousActivitiesOthers = column[Option[String]]("sirTypeSuspiciousActivitiesOthers")
  def sirMethodVerifyOthers = column[Option[String]]("sirMethodVerifyOthers")
  def isCloneToSTR = column[Option[Boolean]]("isCloneToSTR")
  def sirTransactionType = column[Option[String]]("sirTransactionType")
  def playerWith = column[Option[String]]("playerWith")
  def createdDate = column[Option[DateTime]]("createdDate")
  def createdBy = column[Option[UUID]]("createdBy")
  def modifiedDate = column[Option[DateTime]]("modifiedDate")
  def modifiedBy = column[Option[UUID]]("modifiedBy")

  def * = (
    id ::
      sirNo ::
      sirArea ::
      playerId ::
      sirMethodVerify ::
      sirCurrentRelationship ::
      sirAffiliationRelationship ::
      sirTypeSuspiciousActivities ::
      sirTranDateFrom ::
      sirTranDateTo ::
      ctrFormId ::
      ctrFormNo ::
      sirAmount ::
      sirSuspiciousIncidence ::
      sirWatchlist ::
      sirStaffId ::
      sirStaffDepartment ::
      sirDocumentNo ::
      submission ::
      status ::
      recievedDate ::
      recievedBy ::
      review ::
      sirAffiliationRelationshipOthers ::
      sirCurrentRelationshipOthers ::
      sirTypeSuspiciousActivitiesOthers ::
      sirMethodVerifyOthers ::
      isCloneToSTR ::
      sirTransactionType ::
      playerWith ::
      createdDate ::
      createdBy ::
      modifiedDate ::
      modifiedBy :: HNil
    ).mapTo[SirForm]
}
