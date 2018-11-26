package repository

import java.sql.Date
import java.util.UUID

import extensions.CustomColumnTypes._
import javax.inject.{Inject, Singleton}
import models.DbContext._
import models.{Player, PlayerResource, QueryTables}
import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.duration._
import scala.concurrent._

@Singleton
class PlayerRepository @Inject()()(implicit ec: ExecutionContext) {
  def list(query: String, start: Int, limit: Int) = amlContext.run {
    val queryTable = QueryTables.playerQuery.filter(q => (q.playerName like s"%$query%") || (q.passport like s"%$query%") || (q.gercCardNo like s"%$query%"))
    for {
      totalRecords <- queryTable.length.result
      records <- queryTable.drop(start).take(limit).result
    } yield PlayerResource(records, totalRecords)
  }

  def findPlayerById(id: String) = amlContext.run(QueryTables.playerQuery.filter(q => q.id === UUID.fromString(id)).result.head)
}

