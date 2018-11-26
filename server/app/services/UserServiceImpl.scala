package com.aml.services
import java.util.UUID

import auth.models.{BasicInfo, IdentityUser}
import javax.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl @Inject()(
                        implicit ex: ExecutionContext,
                        userRepository: UserRepository,
                      ) extends UserService{

  override def retrieve(loginInfo: LoginInfo): Future[Option[IdentityUser]] = {
    userRepository.getUser(loginInfo).map(userOpt => {
      userOpt match {
        case Some(u) => Some(IdentityUser(u.id, loginInfo, u.loginName, BasicInfo(u.fullName, u.email)))
        case None => Some(IdentityUser(UUID.randomUUID(), loginInfo, Option(""), BasicInfo(Option(""), Option(""))))
      }
    })

  }
}
