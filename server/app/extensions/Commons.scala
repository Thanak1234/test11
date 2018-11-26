package extensions

import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat
import java.util.UUID

import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{JsString, JsValue, Reads, Writes}
import slick.jdbc.H2Profile.api._
import slick.jdbc.PositionedResult

object CustomColumnTypes {
  implicit val jodaDateTimeType = MappedColumnType.base[DateTime, Timestamp](
    dt => new Timestamp(dt.getMillis),
    ts => new DateTime(ts.getTime, UTC)
  )
}

object JsonCustomType {
  val dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss"
  implicit val jodaDateReads = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateTimeFormat))
    )
  )
  implicit val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(d: DateTime): JsValue = JsString(d.toString())
  }

  val dateFormat = new SimpleDateFormat("MM-dd-yyyy")
  implicit val sqlDateWrites: Writes[Date] = new Writes[Date] {
    def writes(d: Date): JsValue = JsString(dateFormat.format(d))
  }
}


