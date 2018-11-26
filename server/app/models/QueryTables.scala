package models

import models.forms._
import repository._
import slick.lifted.TableQuery

object QueryTables {
  val activityQuery: TableQuery[ActivityTable] = TableQuery[ActivityTable]
  val componentLookupQuery: TableQuery[ComponentLookupTable] = TableQuery[ComponentLookupTable]
  val fileAttachmentQuery: TableQuery[FileAttachmentTable] = TableQuery[FileAttachmentTable]
  val locationQuery: TableQuery[LocationTable] = TableQuery[LocationTable]
  val menuQuery: TableQuery[MenuTable] = TableQuery[MenuTable]
  val playerQuery: TableQuery[PlayerTable] = TableQuery[PlayerTable]
  val uploadFileQuery: TableQuery[UploadFileTable] = TableQuery[UploadFileTable]
  val formsQuery: TableQuery[FormsTable] = TableQuery[FormsTable]
  val ctrFormQuery: TableQuery[CtrFormTable] = TableQuery[CtrFormTable]
  val userQuery: TableQuery[UserTable] = TableQuery[UserTable]
  val formAttachmentQuery: TableQuery[FormAttachmentTable] = TableQuery[FormAttachmentTable]
  val sirFormQuery: TableQuery[SirFormTable] = TableQuery[SirFormTable]
  val submissionTransQuery: TableQuery[SubmissionTransTable] = TableQuery[SubmissionTransTable]
  val submissionSummaryQuery: TableQuery[SubmissionSummaryTable] = TableQuery[SubmissionSummaryTable]
  val submissionLogQuery: TableQuery[SubmissionLogTable] = TableQuery[SubmissionLogTable]
  val submissionQuery: TableQuery[SubmissionTable] = TableQuery[SubmissionTable]
  val strFormQuery: TableQuery[StrFormTable] = TableQuery[StrFormTable]
  val countryQuery: TableQuery[CountryTable] = TableQuery[CountryTable]
  val submissionQtyLogQuery: TableQuery[SubmissionQtyLogTable] = TableQuery[SubmissionQtyLogTable]
}
