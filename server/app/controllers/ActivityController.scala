package controllers

import java.util.UUID

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.ActivityRepository
import utils.auth.AuthEnv

import scala.concurrent.{ExecutionContext, Future}

class ActivityController @Inject()(repo: ActivityRepository,
                                   silhouette: Silhouette[AuthEnv],
                                   cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def activitiesByFormId(formId: String) = silhouette.SecuredAction.async {implicit request =>
    val id = UUID.fromString(formId)
    repo.activitiesByFormId(id).map(activities => {
      Ok(Json.toJson(activities))
    })
  }

}
