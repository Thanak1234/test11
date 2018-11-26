package models

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

case class ComponentLookup (id: UUID, parentId: Option[UUID], key: String, value: String, seq: Int, createdDate: DateTime, active: Boolean)

object ComponentLookup {
  implicit val componentLookupFormat = Json.format[ComponentLookup]
}

final class ComponentLookupTable (tag: Tag) extends Table[ComponentLookup](tag, Some("reusable"), "componentlookup") {
  def id = column[UUID]("id", O.PrimaryKey, O.AutoInc)
  def parentId = column[Option[UUID]]("ParentId")
  def key = column[String]("Key")
  def value = column[String]("Value")
  def seq = column[Int]("Seq")
  def createdDate = column[DateTime]("CreatedDate")
  def active = column[Boolean]("Active")
  def * = (id, parentId, key, value, seq, createdDate, active) <> ((ComponentLookup.apply _).tupled, ComponentLookup.unapply)
}
