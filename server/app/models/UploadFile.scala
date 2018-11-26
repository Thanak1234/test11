package models

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._

final case class UploadFile(id: UUID, attachmentId: Option[UUID], fileName: Option[String], fileSize: Option[Long], fileContent: Option[Array[Byte]], uploadedDate: Option[DateTime] = Some(new DateTime()))

object UploadFile {
//  implicit val format = Json.format[UploadFile]
}

final class UploadFileTable (tag: Tag) extends Table[UploadFile](tag, Some("Form"), "UploadFile") {
  def id = column[UUID]("id", O.PrimaryKey, O.SqlType("UNIQUEIDENTIFIER"))
  def attachmentId = column[Option[UUID]]("AttachmentId")
  def fileName = column[Option[String]]("fileName")
  def fileSize = column[Option[Long]]("fileSize")
  def fileContent = column[Option[Array[Byte]]]("fileContent")
  def uploadedDate = column[Option[DateTime]]("uploadedDate")
  def * = (id, attachmentId, fileName, fileSize, fileContent, uploadedDate) <> ((UploadFile.apply _).tupled, UploadFile.unapply)
}

