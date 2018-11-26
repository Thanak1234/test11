package controllers

import java.sql.Date
import java.util.UUID

import com.mohiva.play.silhouette.api.Silhouette
import dto.CtrFormTran
import javax.inject.Inject
import models.forms.CtrForm
import org.joda.time.Seconds
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents, QueryStringBindable}
import repository.{ActivityRepository, FormsRepository}
import repository.forms.CtrFormRepository
import extensions.JsonCustomType._
import services.CtrFormService
import utils.FilterSort
import utils.auth.AuthEnv

import scala.concurrent._

class CtrFormController @Inject()(
                                 formRepo: FormsRepository,
                                 silhouette: Silhouette[AuthEnv],
                                   repo: CtrFormRepository,
                                 ctrFormService: CtrFormService,
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def save = silhouette.SecuredAction.async(parse.json) { implicit request => {
      request.body.validate[CtrFormTran].fold(
        error => Future.successful(InternalServerError("JSON did not validate.")),
        formInfo => {
          repo.save(formInfo, request.identity.copy()).map(r => Ok("Save CTR Form completed."))
        }
      )
    }
  }

  def export(exportType: String = "PDF") = silhouette.SecuredAction.async { implicit request => {
    Future.successful(Ok(ctrFormService.exportPdfXls(exportType)).as("application/octet-stream"))
  }}

  def list(filterSort: FilterSort) = silhouette.SecuredAction.async { implicit request =>
    repo.list(filterSort).map(records => Ok(Json.toJson(records)))
  }

  def findCtrFormById(id: String) = silhouette.SecuredAction.async {
    implicit request =>
      repo.findCtrFormById(id).map(record => Ok(Json.toJson(record)))
  }

  def singleTransactions(dateFrom: Long, excludeCountry: Seq[String], amount: Float) = silhouette.SecuredAction.async {
    implicit request =>
      repo.singleTransaction(new Date(dateFrom), excludeCountry.mkString("|"), amount).map(records => Ok(Json.toJson(records)))
  }

  def multiTransactions(dateFrom: Long, excludeCountry: Seq[String], amount: Float) = silhouette.SecuredAction.async {
    implicit request =>
      repo.multiTransaction(new Date(dateFrom), excludeCountry.mkString("|"), amount).map(records => Ok(Json.toJson(records)))
  }

  def CTRPreview(formIds: String) = silhouette.SecuredAction.async {
    implicit request =>
      repo.CTRPreview(formIds).map(records => Ok(Json.toJson(records)))
  }
}
