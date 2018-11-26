package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.{CountryRepository}
import utils.auth.AuthEnv

import scala.concurrent.ExecutionContext

class CountryController @Inject()(repo: CountryRepository,
                                  silhouette: Silhouette[AuthEnv],
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def getCountries = silhouette.SecuredAction.async { implicit request =>
    repo.findAll.map(records => {
      Ok(Json.toJson(records))
    })
  }

}
