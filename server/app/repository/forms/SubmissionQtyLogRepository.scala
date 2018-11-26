package repository.forms

import java.sql.Date
import java.util.UUID

import dto.ActivityLogDto
import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.DbContext._
import models.forms.SubmissionQtyLog
import models.{Activity, QueryTables}
import org.joda.time.DateTime
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._

@Singleton
class SubmissionQtyLogRepository {

  def getLastQty(form: Option[String]) = {
    val today = DateTime.now()
    amlContext.run(QueryTables.submissionQtyLogQuery.filter(p => p.sentDate === Option(new Date(today.toDate().getTime)) && p.form === form).result.headOption)
  }

  def insert(form: Option[String], qty: Int) = amlContext.run {
    val currentDate = new Date(DateTime.now().toDate().getTime)
    val id = UUID.randomUUID()
    val record = SubmissionQtyLog(id, form, Option(qty), Option(currentDate))
    QueryTables.submissionQtyLogQuery += record
  }

  def updateLastQty(form: Option[String], qty: Int) = {
    val today = DateTime.now()
    QueryTables.submissionQtyLogQuery
      .filter(p => p.sentDate === Option(new Date(today.toDate().getTime)) && p.form === form)
      .map(m => m.lastQty)
      .update(Option(qty))
  }
}



