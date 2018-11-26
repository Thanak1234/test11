package extensions

import java.util.UUID

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import slick.jdbc.PositionedResult

object SQLExtension {
  implicit class SQLPositionedResult(val r: PositionedResult) extends AnyVal {
    def dateFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    def nextUUID() : UUID =  UUID.fromString(r.nextString)
    def nextUUIDOption() : Option[UUID] = r.nextStringOption().map(UUID.fromString(_))
    def nextDateTime() : DateTime =  DateTime.parse(r.nextString, DateTimeFormat.forPattern(dateFormat))
    def nextDateTimeOption() : Option[DateTime] = r.nextStringOption().map(DateTime.parse(_, DateTimeFormat.forPattern(dateFormat)))
  }
}

