package models

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class Forms (id: UUID, form: String, max: Int, createdDate: DateTime)

object Forms {
  implicit val format = Json.format[Forms]
}

final class FormsTable (tag: Tag) extends Table[Forms](tag, Some("reusable"), "forms") {
  def id = column[UUID]("id")
  def form = column[String]("form")
  def max = column[Int]("max")
  def createdDate = column[DateTime]("CreatedDate")
  def * = (id, form, max, createdDate) <> ((Forms.apply _).tupled, Forms.unapply)
}



