package repository

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.DbContext._
import models.{Menu, QueryTables, User}
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class UserRepository {
  def findAll = amlContext.run(QueryTables.userQuery.filter(q => q.status === "Active").result)
  def getUser(loginInfo: LoginInfo) = amlContext.run({
    QueryTables.userQuery.filter(q => q.loginName === loginInfo.providerKey).result.headOption
  })
}



