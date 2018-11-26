package controllers

import java.sql.{Date, Timestamp}

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject._
import models._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._
import repository.PlayerRepository
import utils.auth.AuthEnv

import scala.concurrent.{ExecutionContext, Future}

class JunketController @Inject()(repo: PlayerRepository,
                                 silhouette: Silhouette[AuthEnv],
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

}
