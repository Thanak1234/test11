package controllers

import com.mohiva.play.silhouette
import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.MenuRepository
import utils.auth.AuthEnv

import scala.concurrent.{ExecutionContext, Future}

class MenuController @Inject()(repo: MenuRepository,
                               silhouette: Silhouette[AuthEnv],
                               cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def getMenus = silhouette.SecuredAction.async { implicit request =>
    repo.menus.map(records => {
      Ok(Json.toJson(records))
    })
  }

}
