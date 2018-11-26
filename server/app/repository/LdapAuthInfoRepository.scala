package repository

import com.mohiva.play.silhouette.api.{AuthInfo, LoginInfo}
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.PasswordInfo

import scala.concurrent.Future
import scala.reflect.ClassTag

class LdapAuthInfoRepository extends AuthInfoRepository {
  override def find[T <: AuthInfo](loginInfo: LoginInfo)(implicit tag: ClassTag[T]): Future[Option[T]] = ???

  override def add[T <: AuthInfo](loginInfo: LoginInfo, authInfo: T): Future[T] = ???

  override def update[T <: AuthInfo](loginInfo: LoginInfo, authInfo: T): Future[T] = ???

  override def save[T <: AuthInfo](loginInfo: LoginInfo, authInfo: T): Future[T] = ???

  override def remove[T <: AuthInfo](loginInfo: LoginInfo)(implicit tag: ClassTag[T]): Future[Unit] = ???
}
