package repository

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.exceptions.ConfigurationException
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.{Credentials, PasswordHasherRegistry, PasswordInfo}
import com.mohiva.play.silhouette.impl.exceptions.{IdentityNotFoundException, InvalidPasswordException}
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider.ID
import com.mohiva.play.silhouette.impl.providers.PasswordProvider
import com.mohiva.play.silhouette.impl.providers.PasswordProvider.{HasherIsNotRegistered, PasswordDoesNotMatch, PasswordInfoNotFound}
import com.unboundid.ldap.sdk.{LDAPConnection, LDAPConnectionOptions, ResultCode}
import javax.inject.Inject
import com.unboundid.ldap.sdk.BindResult
import com.unboundid.util.ssl.{SSLUtil, TrustAllTrustManager}
import utils.auth.LdapUtil

import scala.concurrent.{ExecutionContext, Future}

class LdapCredentialsProvider @Inject()(
   protected val authInfoRepository: AuthInfoRepository,
   protected val passwordHasherRegistry: PasswordHasherRegistry,
   protected val ldapUtil: LdapUtil)
(implicit val executionContext: ExecutionContext)

  extends PasswordProvider {
  override def id = LdapCredentialsProvider.ID
  def authenticate(credentials: Credentials): Future[LoginInfo] = {
//    val identifier = if(credentials.identifier.contains("nagaworld\\")) credentials.identifier else s"nagaworld\\${credentials.identifier}"
    ldapUtil.authenticate(credentials.identifier, credentials.password) match {
      case ResultCode.SUCCESS => loginInfo(credentials)
      case ResultCode.CONNECT_ERROR => Future.failed(new InvalidPasswordException(LdapCredentialsProvider.UserPasswordDoesNotMatch))
    }
  }

  def loginInfo(credentials: Credentials): Future[LoginInfo] = Future.successful(LoginInfo(id, credentials.identifier.replace("nagaworld\\", "")))
}

object LdapCredentialsProvider {
  val UserPasswordDoesNotMatch = "User or Passwords does not match"
  val ID = "ldap_credentials"
}