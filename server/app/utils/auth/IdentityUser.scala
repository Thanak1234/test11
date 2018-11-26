package auth.models

import java.util.UUID

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}

case class IdentityUser(
                         id: UUID,
                         loginInfo: LoginInfo,
                         userName: Option[String],
                         basicInfo: BasicInfo,
               ) extends Identity


case class BasicInfo(
                    fullName : Option[String],
                    email: Option[String]
                    )