package models.forms

import java.sql.Date
import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.JsonCustomType._
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

case class FormAttachment(id: Option[UUID], serial: Option[UUID], formId: Option[UUID], name: Option[String],
                          docType: Option[String], identifier: Option[String], docDate: Option[Date], preparer: Option[String],
                          fileName: Option[String], uploadedDate: Option[DateTime], uploadedBy: Option[String])

object FormAttachment {
  implicit val format = Json.format[FormAttachment]
}

final class FormAttachmentTable(tag: Tag) extends Table[FormAttachment](tag, Some("Form"), "FormAttachment") {
  def id = column[Option[UUID]]("id")
  def serial = column[Option[UUID]]("serial")
  def formId = column[Option[UUID]]("formId")
  def name = column[Option[String]]("name")
  def docType = column[Option[String]]("docType")
  def identifier = column[Option[String]]("identifier")
  def docDate = column[Option[Date]]("docDate")
  def preparer = column[Option[String]]("preparer")
  def fileName = column[Option[String]]("fileName")
  def uploadedDate = column[Option[DateTime]]("uploadedDate")
  def uploadedBy = column[Option[String]]("uploadedBy")
  def * = (id, serial, formId, name, docType, identifier,
    docDate, preparer, fileName, uploadedDate, uploadedBy) <> ((FormAttachment.apply _).tupled, FormAttachment.unapply)
}
