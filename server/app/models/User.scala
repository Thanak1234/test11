package models

import java.util.UUID

import auth.models.BasicInfo
import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class User (
  id: UUID,
  loginName: Option[String],
  email: Option[String],
  fullName: Option[String],
  createdBy: Option[UUID],
  createdDate: Option[DateTime],
  modifiedBy: Option[UUID],
  modifiedDate: Option[DateTime],
  status: Option[String],
)

object User {
  implicit val format = Json.format[User]
}


final class UserTable (tag: Tag) extends Table[User](tag, Some("app"), "users") {

  def id = column[UUID]("id", O.PrimaryKey)
  def loginName = column[Option[String]]("loginName")
  def email = column[Option[String]]("email")
  def fullName = column[Option[String]]("fullName")
  def createdBy = column[Option[UUID]]("createdBy")
  def createdDate = column[Option[DateTime]]("createdDate")
  def modifiedBy = column[Option[UUID]]("modifiedBy")
  def modifiedDate = column[Option[DateTime]]("modifiedDate")
  def status = column[Option[String]]("status")

  def * = (id, loginName, email, fullName, createdBy, createdDate, modifiedBy, modifiedDate, status) <> ((User.apply _).tupled, User.unapply)
}


