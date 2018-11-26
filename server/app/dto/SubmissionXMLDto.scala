package dto

import java.sql.Date

import org.joda.time.DateTime
import play.api.libs.json.Json
import extensions.SQLExtension._
import extensions.CustomColumnTypes._
import slick.jdbc.GetResult
import extensions.JsonCustomType._

final case class SubmissionXMLDto(
                                   saveDate: Option[DateTime],
                                   sentDate: Option[DateTime],
                                   formType: Option[String],
                                   actionType: Option[String],
                                  tranDate: Option[Date],
                                   amount: Option[Float],
                                   playerName: Option[String],
                                  dob: Option[Date],
                                   driverLicense: Option[String],
                                   gercCardNo: Option[String],
                                  passport: Option[String],
                                   idCard: Option[String],
                                   others: Option[String],
                                  gender: Option[String],
                                   dateExpired: Option[Date],
                                   transCategory: Option[String],
                                   isoCode: Option[String],
                                  transType: Option[String],
                                  sirSuspiciousIncidence: Option[String],
                                  reportIdentifier: Option[String])
object SubmissionXMLDto {
  implicit val format = Json.format[SubmissionXMLDto]
  implicit val getSubmissionXMLResult = GetResult(r => SubmissionXMLDto(
    r.nextDateTimeOption(),
    r.nextDateTimeOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextDateOption(),
    r.nextFloatOption(),
    r.nextStringOption(),
    r.nextDateOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextDateOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption()
  ))
}
