package repository

import java.util.UUID

import dto.ActivityLogDto
import extensions.CustomColumnTypes._
import javax.inject.Singleton
import models.{Activity, ComponentLookup, QueryTables}
import models.DbContext._
import org.joda.time.DateTime
import slick.collection.heterogeneous.HNil
import slick.jdbc.SQLServerProfile.api._

@Singleton
class ActivityRepository {
  def insertActivity(activity: Activity) = QueryTables.activityQuery += activity
  def activitiesByFormId(formId: UUID) = amlContext.run {
    QueryTables.userQuery.join(QueryTables.activityQuery).on({ case (u, a) => u.id === a.createdBy }).filter(q => q._2.formId === formId)
      .map({ case (u, a) => (a.id :: a.formId :: a.action :: a.comment :: a.createdDate :: u.fullName :: HNil).mapTo[ActivityLogDto]})
      .result
  }
}



