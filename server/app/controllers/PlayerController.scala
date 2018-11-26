package controllers

import java.util.UUID

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import repository.PlayerRepository
import utils.auth.AuthEnv

import scala.concurrent.{ExecutionContext, Future}

class PlayerController @Inject()(repo: PlayerRepository,
                                 silhouette: Silhouette[AuthEnv],
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def getPlayers(query: String, start: Int, limit: Int) = silhouette.SecuredAction.async { implicit request =>
    repo.list(query, start, limit).map(records => {
      Ok(Json.toJson(records))
    })
  }

  def findPlayerById(id: String) = silhouette.SecuredAction.async {
    implicit request =>
      repo.findPlayerById(id).map(result => Ok(Json.toJson(result)))
  }
}
