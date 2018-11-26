package models.forms

import java.sql.Date
import java.util.UUID

import models.Player
import org.joda.time.DateTime
import play.api.libs.json._
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

case class CtrForm(
                    var id : Option[UUID],
                    var formNo : Option[String],
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
                    createdDate : Option[DateTime],
                    createdBy : Option[UUID],
                    modifiedDate : Option[DateTime],
                    modifiedBy : Option[UUID],
                    submission : Option[Boolean],
                    status : Option[String],
                    playerWith : Option[String]
)

object CtrForm {
  implicit val format = Json.format[CtrForm]
}

final class CtrFormTable (tag: Tag) extends Table[CtrForm](tag, Some("form"), "CtrForm") {
  def id = column[Option[UUID]]("id")
  def no  = column[Option[String]]("no")
  def typeOf = column[Option[String]]("type")
  def tran  = column[Option[String]]("tran")
  def location  = column[Option[String]]("location")
  def area  = column[Option[String]]("area")
  def playerId  = column[Option[UUID]]("playerId")
  def tranDate  = column[Option[DateTime]]("tranDate")
  def docNo  = column[Option[String]]("docNo")
  def amount  = column[Option[Float]]("amount")
  def staffId  = column[Option[String]]("staffId")
  def staffDepartment  = column[Option[String]]("staffDepartment")
  def remark  = column[Option[String]]("remark")
  def createdDate  = column[Option[DateTime]]("createdDate")
  def createdBy  = column[Option[UUID]]("createdBy")
  def modifiedDate  = column[Option[DateTime]]("modifiedDate")
  def modifiedBy  = column[Option[UUID]]("modifiedBy")
  def submission  = column[Option[Boolean]]("submission")
  def status  = column[Option[String]]("status")
  def playerWith  = column[Option[String]]("playerWith")
  def * = (id, no, typeOf, tran, location, area, playerId, tranDate, docNo, amount, staffId,
    staffDepartment, remark, createdDate, createdBy, modifiedDate, modifiedBy,
    submission, status, playerWith) <> ((CtrForm.apply _).tupled, CtrForm.unapply)
}