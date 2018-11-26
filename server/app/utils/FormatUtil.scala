package utils

import java.text.SimpleDateFormat

import org.joda.time.DateTime

object FormatUtil {
  def dateFormat(date: DateTime, format: String) = new SimpleDateFormat(format).format(date.toDate)
}
