package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.LocationRepository
import utils.auth.AuthEnv

import scala.concurrent.ExecutionContext

class LocationController @Inject()(repo: LocationRepository,
                                   silhouette: Silhouette[AuthEnv],
                                   cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def getLocationByLocnCode(locnType: String) = silhouette.SecuredAction.async { implicit request =>
    repo.getLocationByLocnType(locnType).map(p => {
      Ok(Json.toJson(p))
    })
  }
}
