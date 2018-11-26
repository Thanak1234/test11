package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.ComponentLookupRepository
import utils.auth.AuthEnv

import scala.concurrent.ExecutionContext

class ComponentLookupController @Inject()(repo: ComponentLookupRepository,
                                          silhouette: Silhouette[AuthEnv],
                                          cc: MessagesControllerComponents
                                         )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def getLookups(key: String) = silhouette.SecuredAction.async { implicit request =>
    repo.list(key).map { records =>
      Ok(Json.toJson(records))
    }
  }

}
