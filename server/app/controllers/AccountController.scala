package controllers

import java.util.UUID

import javax.inject.Inject
import com.mohiva.play.silhouette.api.Authenticator.Implicits._
import com.mohiva.play.silhouette.api.{Silhouette, _}
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.{Clock, Credentials}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import dto.LoginInfo
import net.ceedubs.ficus.Ficus._
import play.api.Configuration
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import repository.{LdapCredentialsProvider, UserRepository}
import java.nio.file.Files

import com.aml.services.UserService
import utils.auth.AuthEnv

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class AccountController @Inject()(silhouette: Silhouette[AuthEnv],
                                  userService: UserService,
                                  authInfoRepository: AuthInfoRepository,
                                  credentialsProvider: LdapCredentialsProvider,
                                  configuration: Configuration,
                                  userRepository: UserRepository,
                                  clock: Clock,
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def singOut = silhouette.SecuredAction.async { implicit request =>
    silhouette.env.eventBus.publish(LogoutEvent(request.identity, request))
    silhouette.env.authenticatorService.discard(request.authenticator,
      Ok("Sign out completed.")
    )
  }

  implicit val dataReads = (
    (__ \ 'username).read[String] and
      (__ \ 'password).read[String]
    )(LoginInfo.Data.apply _)

  def login = Action.async(parse.json) { implicit request =>
    request.body.validate[LoginInfo.Data].map { data =>
      credentialsProvider.authenticate(Credentials(data.user, data.password)).flatMap { loginInfo =>
        userService.retrieve(loginInfo).flatMap {
          case Some(user) => silhouette.env.authenticatorService.create(loginInfo).map {
            case authenticator => authenticator
          }.flatMap { authenticator =>
            silhouette.env.eventBus.publish(LoginEvent(user, request))
            silhouette.env.authenticatorService.init(authenticator).map { token =>
              Ok(Json.obj("token" -> token))
            }
          }
          case None => Future.failed(new IdentityNotFoundException("Couldn't find user"))
        }
      }.recover {
        case e: ProviderException =>
          Unauthorized(Json.obj("message" -> Messages("invalid.credentials")))
      }
    }.recoverTotal {
      case error =>
        Future.successful(Unauthorized(Json.obj("message" -> Messages("invalid.credentials"))))
    }
  }

  def getUsers = Action.async { implicit request =>
    userRepository.findAll.map(records => Ok(Json.toJson(records)))
  }

}
