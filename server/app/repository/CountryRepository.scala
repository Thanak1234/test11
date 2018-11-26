package repository

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Singleton
import models.DbContext._
import models.QueryTables
import slick.jdbc.SQLServerProfile.api._

@Singleton
class CountryRepository {
  def findAll = amlContext.run(QueryTables.countryQuery.filter(q => q.status === "Active").result)
}



