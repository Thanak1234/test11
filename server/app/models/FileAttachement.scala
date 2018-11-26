package models

import java.util.UUID

import org.joda.time.DateTime
import extensions.JsonCustomType._
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class FileAttachment(
                                 var id: Option[UUID],
                                 serial: Option[UUID],
                                 var formId: Option[UUID],
                                 name: Option[String],
                                 description: Option[String],
                                 var fileName: Option[String],
                                 var uploadedDate: Option[DateTime] = Some(new DateTime()),
                                 var uploadedBy: Option[String] = None
                           )

object FileAttachment {
  implicit val format = Json.format[FileAttachment]
}

final class FileAttachmentTable (tag: Tag) extends Table[FileAttachment](tag, Some("Form"), "Attachment") {
  def id = column[Option[UUID]]("id", O.PrimaryKey)
  def serial = column[Option[UUID]]("serial")
  def formId = column[Option[UUID]]("formId")
  def name = column[Option[String]]("name")
  def desc = column[Option[String]]("desc")
  def fileName = column[Option[String]]("fileName")
  def uploadedDate = column[Option[DateTime]]("uploadedDate")
  def uploadedBy = column[Option[String]]("uploadedBy")
  def * = (id, serial, formId, name, desc, fileName, uploadedDate, uploadedBy) <> ((FileAttachment.apply _).tupled, FileAttachment.unapply)
}
