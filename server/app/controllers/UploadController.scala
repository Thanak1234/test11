package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.{FileAttachmentRepository, FormAttachmentRepository, UploadFileRepository}
import java.nio.file.{Files, Paths}
import java.util.UUID

import com.mohiva.play.silhouette.api.Silhouette
import extensions.JsonCustomType._
import models.forms.FormAttachment
import models.{FileAttachment, UploadFile}
import org.joda.time.DateTime
import utils.auth.AuthEnv

import scala.concurrent._
import scala.concurrent.duration._

class UploadController @Inject()(
                                  uploadFileRepo: UploadFileRepository,
                                  silhouette: Silhouette[AuthEnv],
                                  fileAttachmentRepo: FileAttachmentRepository,
                                  formAttachmentRepository: FormAttachmentRepository,
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {
    def uploadFile() = silhouette.SecuredAction.async(parse.multipartFormData) { implicit request =>
      request.body.dataParts.get("uploadInfo") match  {
        case Some(upload) => {
          val fileAttachment = Json.parse(upload.head).as[FileAttachment]
          val file = request.body.files.headOption.get
          val attchmentId = Some(UUID.randomUUID())
          fileAttachment.id = attchmentId
          fileAttachment.uploadedBy = request.identity.basicInfo.fullName
          fileAttachment.fileName = Some(file.filename)
          fileAttachment.uploadedDate = Some(new DateTime())
          val fileContent = Files.readAllBytes(Paths.get(file.ref.getAbsolutePath))
          val uploadFile = UploadFile(UUID.randomUUID(), attchmentId, Some(file.filename), Some(file.ref.length()), Some(fileContent))
          fileAttachmentRepo.save(fileAttachment, uploadFile).map( record => {
            file.ref.delete()
            Ok(Json.toJson(record))
          })
        }
        case None => Future.successful(BadRequest("Upload's information not found."))
      }
    }

  def frmUploadFile = silhouette.SecuredAction.async(parse.multipartFormData) { implicit request =>
    request.body.dataParts.get("uploadInfo") match {
      case Some(upload) => {
        val file = request.body.files.headOption.get
        val defaultDate = Option(new DateTime())
        val temp = Json.parse(upload.head).as[FormAttachment]
        val attachment = FormAttachment(Option(UUID.randomUUID()), temp.serial, None, temp.name, temp.docType,
          temp.identifier, temp.docDate, temp.preparer, Option(file.filename), defaultDate, request.identity.basicInfo.fullName)
        val fileContent = Files.readAllBytes(Paths.get(file.ref.getAbsolutePath))
        val uploadFile = UploadFile(UUID.randomUUID(), attachment.id, Some(file.filename), Some(file.ref.length()), Some(fileContent))
        formAttachmentRepository.save(attachment, uploadFile).map(record => {
          file.ref.delete()
          Ok(Json.toJson(record))
        })
      }
      case None => Future.successful(BadRequest("Upload's information not found."))
    }
  }

  def findAttachmentByFormId(formId: String) = silhouette.SecuredAction.async {
    implicit request =>
      fileAttachmentRepo.findFileAttachmentByFormId(UUID.fromString(formId)).map(records => {
        Ok(Json.toJson(records))
      })
  }

  def findFormAttachmentByFormId(formId: String) = silhouette.SecuredAction.async {
    implicit request =>
      formAttachmentRepository.findFormAttachmentByFormId(UUID.fromString(formId)).map(records => {
        Ok(Json.toJson(records))
      })
  }
}
