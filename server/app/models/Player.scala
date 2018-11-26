package models
import java.sql.Date
import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json._
import extensions.JsonCustomType._
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class Player(
                         id: Option[UUID],
                         playerCardType: Option[String],
                         playerCardNo: Option[String],
                         playerDateIssue: Option[Date],
                         playerDateExpired: Option[Date],
                         playerName: Option[String],
                         playerNickName: Option[String],
                         playerGender: Option[String],
                         playerNationality: Option[String],
                         playerCountry: Option[String],
                         playerPhone: Option[String],
                         playerAddress: Option[String],
                         createdDate: Option[DateTime],
                         createdBy: Option[Int],
                         modifiedDate: Option[DateTime],
                         modifiedBy: Option[Int],
                         driverLicense: Option[String],
                         gercCardNo: Option[String],
                         idCard: Option[String],
                         passport: Option[String],
                         others: Option[String],
                         dob: Option[Date],
                         noCard: Option[String],
                         activePlayerId: Option[Int],
                         mergedLogId: Option[Int],
                         ecdd: Option[Boolean])

object Player {
  implicit val playerFormat = JsFormatter
}

final class PlayerTable(tag: Tag) extends Table[Player](tag, Some("form"), "player") {
  def id = column[Option[UUID]]("id")
  def playerCardType = column[Option[String]]("playerCardType")
  def playerCardNo = column[Option[String]]("playerCardNo")
  def playerDateIssue = column[Option[Date]]("playerDateIssue")
  def playerDateExpired = column[Option[Date]]("playerDateExpired")
  def playerName= column[Option[String]]("playerName")
  def playerNickName= column[Option[String]]("playerNickName")
  def playerGender= column[Option[String]]("playerGender")
  def playerNationality= column[Option[String]]("playerNationality")
  def playerCountry= column[Option[String]]("playerCountry")
  def playerPhone= column[Option[String]]("playerPhone")
  def playerAddress= column[Option[String]]("playerAddress")
  def createdDate = column[Option[DateTime]]("createdDate")
  def createdBy = column[Option[Int]]("createdBy")
  def modifiedDate = column[Option[DateTime]]("modifiedDate")
  def modifiedBy = column[Option[Int]]("modifiedBy")
  def driverLicense= column[Option[String]]("driverLicense")
  def gercCardNo= column[Option[String]]("gercCardNo")
  def idCard= column[Option[String]]("idCard")
  def passport= column[Option[String]]("passport")
  def others= column[Option[String]]("others")
  def dob = column[Option[Date]]("dob")
  def noCard= column[Option[String]]("noCard")
  def activePlayerId = column[Option[Int]]("activePlayerId")
  def mergedLogId = column[Option[Int]]("mergedLogId")
  def ecdd = column[Option[Boolean]]("ecdd")

  def * = (id :: playerCardType :: playerCardNo :: playerDateIssue ::
    playerDateExpired :: playerName :: playerNickName :: playerGender ::
    playerNationality :: playerCountry :: playerPhone :: playerAddress ::
    createdDate :: createdBy :: modifiedDate :: modifiedBy :: driverLicense ::
    gercCardNo :: idCard :: passport :: others :: dob :: noCard ::
    activePlayerId :: mergedLogId :: ecdd :: HNil).mapTo[Player]
}


object JsFormatter extends Format[Player] {
  override def reads(json: JsValue): JsResult[Player] = JsSuccess(
    Player(
      (json \ "id").asOpt[UUID],
      (json \ "playerCardType").asOpt[String],
      (json \ "playerCardNo").asOpt[String],
      (json \ "playerDateIssue").asOpt[Date],
      (json \ "playerDateExpired").asOpt[Date],
      (json \ "playerName").asOpt[String],
      (json \ "playerNickName").asOpt[String],
      (json \ "playerGender").asOpt[String],
      (json \ "playerNationality").asOpt[String],
      (json \ "playerCountry").asOpt[String],
      (json \ "playerPhone").asOpt[String],
      (json \ "playerAddress").asOpt[String],
      (json \ "createdDate").asOpt[DateTime],
      (json \ "createdBy").asOpt[Int],
      (json \ "modifiedDate").asOpt[DateTime],
      (json \ "modifiedBy").asOpt[Int],
      (json \ "driverLicense").asOpt[String],
      (json \ "gercCardNo").asOpt[String],
      (json \ "idCard").asOpt[String],
      (json \ "passport").asOpt[String],
      (json \ "others").asOpt[String],
      (json \ "dob").asOpt[Date],
      (json \ "noCard").asOpt[String],
      (json \ "activePlayerId").asOpt[Int],
      (json \ "mergedLogId").asOpt[Int],
      (json \ "ecdd").asOpt[Boolean]
    )
  )

  override def writes(o: Player): JsValue = Json.obj(
    "id" -> o.id,
    "playerCardType" -> o.playerCardType,
    "playerCardNo" -> o.playerCardNo,
    "playerDateIssue" -> o.playerDateIssue,
    "playerDateExpired" -> o.playerDateExpired,
    "playerName" -> o.playerName,
    "playerNickName" -> o.playerNickName,
    "playerGender" -> o.playerGender,
    "playerNationality" -> o.playerNationality,
    "playerCountry" -> o.playerCountry,
    "playerPhone" -> o.playerPhone,
    "playerAddress" -> o.playerAddress,
    "createdDate" -> o.createdDate,
    "createdBy" -> o.createdBy,
    "modifiedDate" -> o.modifiedDate,
    "modifiedBy" -> o.modifiedBy,
    "driverLicense" -> o.driverLicense,
    "gercCardNo" -> o.gercCardNo,
    "idCard" -> o.idCard,
    "passport" -> o.passport,
    "others" -> o.others,
    "dob" -> o.dob,
    "noCard" -> o.noCard,
    "activePlayerId" -> o.activePlayerId,
    "mergedLogId" -> o.mergedLogId,
    "ecdd" -> o.ecdd
  )
}