package models

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.{GetResult, PositionedResult}
import extensions.CustomColumnTypes._
import extensions.JsonCustomType._
import org.joda.time.format.DateTimeFormat
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class Menu (
  id: UUID,
  parentId: Option[UUID],
  name: Option[String],
  formCls: Option[String],
  iconCls: Option[String],
  routeId: Option[String],
  leaf: Option[Boolean],
  createdBy: Option[UUID],
  createdDate: Option[DateTime],
  modifiedBy: Option[UUID],
  modifiedDate: Option[DateTime],
  status: Option[String]
)

object Menu {
  implicit val format = Json.format[Menu]
}

final class MenuTable (tag: Tag) extends Table[Menu](tag, Some("app"), "menu") {

  def id = column[UUID]("id")
  def parentId = column[Option[UUID]]("parentId")
  def name = column[Option[String]]("name")
  def formCls = column[Option[String]]("formCls")
  def iconCls = column[Option[String]]("iconCls")
  def routeId = column[Option[String]]("routeId")
  def leaf = column[Option[Boolean]]("leaf")
  def createdBy = column[Option[UUID]]("createdBy")
  def createdDate = column[Option[DateTime]]("createdDate")
  def modifiedBy = column[Option[UUID]]("modifiedBy")
  def modifiedDate = column[Option[DateTime]]("modifiedDate")
  def status = column[Option[String]]("status")

  def * = (id, parentId, name, formCls, iconCls, routeId, leaf, createdBy, createdDate, modifiedBy, modifiedDate, status) <> ((Menu.apply _).tupled, Menu.unapply)
}