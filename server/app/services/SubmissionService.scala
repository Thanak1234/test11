package services

import java.util.UUID

import javax.inject.{Inject, Singleton}
import javax.mail.Message.RecipientType
import javax.mail.Session
import javax.mail.util.ByteArrayDataSource
import libs.{AttachmentResource, PGPMailInfo, Recipient}
import repository.forms.{SubmissionRepository, SubmissionSummaryRepository}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import libs.PGPConfig._
import libs.MailConfig._
import models.forms.Submission
import org.joda.time.DateTime
import utils.FormatUtil

@Singleton
class SubmissionService @Inject() (
                                  submissionRepository: SubmissionRepository,
                                  submissionSummaryRepository: SubmissionSummaryRepository
                                  ) (implicit ec: ExecutionContext){
  def getCtrXML(submissionId: String, formType: String) = {
    submissionSummaryRepository.updateReportIdentifier(Option(submissionId), Option(formType))
    submissionRepository.getCtrSubmissionXML(submissionId).flatMap(records => {
      Future.successful(templates.xml.ctr.render(records.toSeq))
    })
  }

  def send(submissionId: String, formType: String) = {
   formType match {
     case "CTR" => {
       getCtrXML(submissionId, formType).flatMap(xml => {
         val bytes = xml.body.getBytes
         val name = s"${UUID.randomUUID()}.xml"
         val datasource = getAttachment(name, bytes, "application/xml")
         val attachment = new AttachmentResource(name, datasource)
         sendXML(Seq(attachment), "CTR Form")
         Future.successful("CTR has been sent...")
       })
     }
     case "STR" => {
       Future.successful("STR has been sent...")
     }
   }
  }

  def getAttachment(name: String, content: Array[Byte], mimetype: String) = {
    val dataSource = new ByteArrayDataSource(content, mimetype)
    dataSource.setName(name)
    dataSource
  }

  def sendXML(attachements: Seq[AttachmentResource], formType: String) = {
    val currentDate = FormatUtil.dateFormat(DateTime.now(), "yyyy-MM-dd hh:mm:ss")
    val mailInfo = PGPMailInfo(s"$formType-$currentDate", "", Seq.empty, attachements)
    val service = new PGPMailService(mailInfo)
    service.send
  }

}
