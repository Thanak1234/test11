package repository.forms
import java.util.UUID

import dto.{ActivityLogDto, TransactionDto}
import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.{ComponentLookup, QueryTables}
import models.DbContext._
import models.forms.SubmissionLog
import org.joda.time.DateTime
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._

@Singleton
class SubmissionLogRepository {
  def save(log: SubmissionLog) = QueryTables.submissionLogQuery += log
}
