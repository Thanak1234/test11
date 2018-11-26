package models

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class Location (
  id: UUID,
  locnId: Option[Int],
  locnCode: Option[String],
  locnName: Option[String],
  locnType: Option[String],
  status: Option[String],
  createdDate: Option[DateTime]
)

object Location {
  implicit val format = Json.format[Location]
}

final class LocationTable(tag: Tag) extends Table[Location](tag, Some("form"), "location") {
  def id = column[UUID]("id")
  def locnId = column[Option[Int]]("locnId")
  def locnCode = column[Option[String]]("locnCode")
  def locnName = column[Option[String]]("locnName")
  def locnType = column[Option[String]]("locnType")
  def createdDate = column[Option[DateTime]]("createdDate")
  def status = column[Option[String]]("status")

  def * = (id, locnId, locnCode, locnName, locnType, status, createdDate) <> ((Location.apply _).tupled, Location.unapply)
}


