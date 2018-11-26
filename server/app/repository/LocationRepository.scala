package repository

import java.util.UUID

import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.DbContext._
import models.{Location, Menu, QueryTables}
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class LocationRepository {
  def getLocationByLocnType(locnType: String) = amlContext.run {
    QueryTables.locationQuery.filter(p => p.locnType === locnType && p.status === "Active").sortBy(s => s.locnCode).result
  }
}


