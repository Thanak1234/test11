package repository

import java.util.UUID

import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.{Forms, QueryTables}
import models.DbContext._
import org.joda.time.DateTime
import slick.jdbc.SQLServerProfile.api._

@Singleton
class FormsRepository {
  def getFormMax(form: String) = QueryTables.formsQuery.filter(p => p.form === form).map(m => m.max).result.head
  def updateMax(form: String, max: Int) = QueryTables.formsQuery.filter(_.form === Option(form)).map(_.max).update(max)
}

