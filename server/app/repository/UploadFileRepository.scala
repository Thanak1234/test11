package repository

import java.util.UUID

import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.{QueryTables, UploadFile}
import models.DbContext._
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class UploadFileRepository {

  def save(uploadFile: UploadFile) = amlDocContext.run {
    QueryTables.uploadFileQuery += uploadFile
  }

  def delectByAttachmentId(attachmentId: Option[UUID]) = QueryTables.uploadFileQuery.filter(q => q.attachmentId === attachmentId).delete

}


