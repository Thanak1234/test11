package repository

import java.util.UUID

import com.google.inject.Inject
import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.DbContext._
import models.forms.FormAttachment
import models.{FileAttachment, QueryTables, UploadFile}
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class FileAttachmentRepository @Inject() (uploadFileRepo: UploadFileRepository) {

  def findFileAttachmentByFormId(formId: UUID) = amlDocContext.run(QueryTables.fileAttachmentQuery.filter(q => q.formId === formId).result)

  def save(fileAttachment: FileAttachment, uploadFile: UploadFile) = amlDocContext.run {
    ((QueryTables.fileAttachmentQuery += fileAttachment) andThen
      (QueryTables.uploadFileQuery += uploadFile) andThen
      QueryTables.fileAttachmentQuery.filter(p => p.id === fileAttachment.id).result.head)
      .transactionally
  }

  def updateFileAttachment(records: List[FileAttachment], id: Option[UUID]) = records.map(r => amlDocContext.run(
    QueryTables.fileAttachmentQuery.filter(q => q.id === r.id).map(_.formId).update(id)))

  def deleteFileAttachment(records: List[FileAttachment]) = records.map(r => amlDocContext.run(
    (QueryTables.fileAttachmentQuery.filter(q => q.id === r.id).delete andThen uploadFileRepo.delectByAttachmentId(r.id))
      .transactionally))

  def updateFormId(id: Option[UUID], formId: Option[UUID]) = amlDocContext.run(QueryTables.fileAttachmentQuery.filter(q => q.id === id).map(_.formId).update(formId))
}



