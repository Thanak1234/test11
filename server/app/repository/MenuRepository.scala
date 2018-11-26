package repository

import java.util.UUID

import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.{Menu, QueryTables}
import models.DbContext._
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class MenuRepository {
  def menus = amlContext.run {
    QueryTables.menuQuery.result
  }
}



