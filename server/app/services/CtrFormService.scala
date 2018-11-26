package services

import javax.inject.Singleton
import utils.{FilterSort, Report}

@Singleton
class CtrFormService {
  def exportPdfXls(exportType: String) = Report.render("/FORMS/Test", exportType, new java.util.HashMap[String, String]())
}
