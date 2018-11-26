package controllers

import java.util.UUID

import com.mohiva.play.silhouette.api.Silhouette
import dto.{StrFormTran, SubmissionTran}
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.ActivityRepository
import repository.forms.SubmissionRepository
import services.SubmissionService
import utils.FilterSort
import utils.auth.AuthEnv

import scala.concurrent.{ExecutionContext, Future}

class SubmissionController @Inject()(repo: SubmissionRepository,
                                     submissionService: SubmissionService,
                                     silhouette: Silhouette[AuthEnv],
                                     cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def list(filterSort: FilterSort) = silhouette.SecuredAction.async { implicit request =>
    repo.list(filterSort).map(records => Ok(Json.toJson(records)))
  }

  def save = silhouette.SecuredAction.async(parse.json) { implicit request => {
      request.body.validate[SubmissionTran].fold(
        error => Future.successful(InternalServerError("JSON did not validate.")),
        params => {
          repo.save(params.criteria, params.transactions, request.identity.copy()).map(m => Ok("Submission save completed."))
        }
      )
    }
  }

  def xml = Action.async(implicit request => {
    submissionService.send("862EA9CD-E783-764A-9086-D4057FBB8D26", "CTR").map(result => {
      Ok(result)
    })
  })

}
