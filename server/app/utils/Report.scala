package utils

import java.util._

import com.masse.ssrs.ReportingServices

object Report {

  val PDF = "PDF"
  val XLS = "EXCEL"

  def render(reportPath: String, format: String, parameters: Map[String, String]) = {
    var result = ReportingServices.getReport(format, "http://10.60.0.112/reportserver/reportexecution2005.asmx", reportPath, "eng-eng", parameters)
    result.value
  }

}
