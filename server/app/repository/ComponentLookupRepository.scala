package repository

import java.util.UUID

import javax.inject.Singleton
import models.{ComponentLookup, QueryTables}
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._
import extensions.CustomColumnTypes._
import models.DbContext._

@Singleton
class ComponentLookupRepository {
  def list(key: String) = amlContext.run {
    QueryTables.componentLookupQuery.filter(p => p.key === key).sortBy(s => s.seq).result
  }
}



