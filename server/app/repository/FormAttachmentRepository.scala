package repository

import java.sql.Date
import java.util.UUID

import extensions.CustomColumnTypes._
import javax.inject.{Inject, Singleton}
import models.DbContext._
import models.forms.FormAttachment
import models.{FileAttachment, QueryTables, UploadFile}
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class FormAttachmentRepository @Inject()(
                                        uploadFileRepo: UploadFileRepository
                                        ) {
  def findFormAttachmentByFormId(formId: UUID) = amlDocContext.run(QueryTables.formAttachmentQuery.filter(q => q.formId === formId).result)

  def save(fileAttachment: FormAttachment, uploadFile: UploadFile) = amlDocContext.run {
    ((QueryTables.formAttachmentQuery += fileAttachment) andThen
      (QueryTables.uploadFileQuery += uploadFile) andThen
      QueryTables.formAttachmentQuery.filter(p => p.id === fileAttachment.id).result.head)
      .transactionally
  }

  def updateFormAttachment(records: Seq[FormAttachment], id: Option[UUID]) = records.map(r => amlDocContext.run(
    QueryTables.formAttachmentQuery.filter(q => q.id === r.id).map(_.formId).update(id)))

  def deleteFormAttachment(records: Seq[FormAttachment]) = records.map(r => amlDocContext.run(
    (QueryTables.formAttachmentQuery.filter(q => q.id === r.id).delete andThen uploadFileRepo.delectByAttachmentId(r.id))
      .transactionally))

  def updateFormId(id: Option[UUID], formId: Option[UUID]) = amlDocContext.run(QueryTables.formAttachmentQuery.filter(q => q.id === id).map(_.formId).update(formId))
}




