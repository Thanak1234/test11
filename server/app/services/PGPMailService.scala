package services

import java.util.Date

import javax.activation.DataHandler
import javax.mail.Message.RecipientType
import javax.mail.{Session, Transport}
import javax.mail.internet.MimeUtility
import javax.mail.internet._
import libs.MailConfig.{port, webmailUrl}
import libs._
import libs.PGPConfig._


class PGPMailService (mailInfo: PGPMailInfo) {

  val props = System.getProperties()
  props.put("mail.smtp.host", webmailUrl)
  props.put("mail.smtp.port", port)

  val session = Session.getDefaultInstance(props, null)
  val CHARACTER_ENCODING = "UTF-8"
  val pgpMail = new PGPMail(executablePath, new DefaultAuthenticator(fromAddress, passphrase), Array(reciepentAddress))

  def getBodyPartFromDatasource(resource: AttachmentResource) = {
    val attachmentPart = new MimeBodyPart()
    attachmentPart.setDataHandler(new DataHandler(resource.datasource))
    attachmentPart.setHeader("Content-Type", s"""${resource.datasource.getContentType()}; name="${resource.name}" """)
    attachmentPart.setHeader("Content-Disposition", s"""attachment; filename="${resource.name}" """)
    attachmentPart.setHeader("Content-Transfer-Encoding", "quoted-printable")
    attachmentPart
  }

  def prepareMessage = {
    val message = new MimeMessage(session)
    message.setSubject(mailInfo.subject, CHARACTER_ENCODING)
    message.setFrom(new InternetAddress(fromAddress, "AML", CHARACTER_ENCODING))
    message.addRecipient(RecipientType.TO, new InternetAddress(reciepentAddress, "CAFUI", CHARACTER_ENCODING))
    val multipartRoot = new MimeMultipart("related")
    mailInfo.attachments.foreach(a => multipartRoot.addBodyPart(getBodyPartFromDatasource(a)))
    mailInfo.headers.foreach(h => {
      val headerName = h._1
      val headerValue = MimeUtility.encodeText(h._2, CHARACTER_ENCODING, null)
      val foldedHeaderValue = MimeUtility.fold(headerName.length + 2, headerValue)
      message.addHeader(headerName, foldedHeaderValue)
    })
    val encrypted = pgpMail.signedAndEncrypted(multipartRoot)
    message.setContent(encrypted)
    message.setSentDate(new Date())
    message.saveChanges()
    message
  }

  def send = {
    val message = prepareMessage
    Transport.send(message)
  }

}

object PGPMailService