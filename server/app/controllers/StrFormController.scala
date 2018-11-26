package controllers

import com.mohiva.play.silhouette.api.Silhouette
import dto.{StrFormTran}
import extensions.JsonCustomType._
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.FormsRepository
import repository.forms.{StrFormRepository}
import utils.FilterSort
import utils.auth.AuthEnv

import scala.concurrent._

class StrFormController @Inject()(
                                 formRepo: FormsRepository,
                                 silhouette: Silhouette[AuthEnv],
                                 repo: StrFormRepository,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def save = silhouette.SecuredAction.async(parse.json) { implicit request => {
      request.body.validate[StrFormTran].fold(
        error => Future.successful(InternalServerError("JSON did not validate.")),
        formInfo => {
          repo.save(formInfo, request.identity.copy()).map(r => Ok("Save STR Form completed."))
        }
      )
    }
  }

//  def export(exportType: String = "PDF") = silhouette.SecuredAction.async { implicit request => {
//    Future.successful(Ok(ctrFormService.exportPdfXls(exportType)).as("application/octet-stream"))
//  }}

  def list(filterSort: FilterSort) = silhouette.SecuredAction.async { implicit request =>
    repo.list(filterSort).map(records => Ok(Json.toJson(records)))
  }
}
