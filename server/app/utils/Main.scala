package utils

import java.util
import java.util.Date

import javax.mail.{Message, Session, Transport}
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import libs.{DefaultAuthenticator, PGPMail}
import libs.PGPConfig.{executablePath, fromAddress, passphrase, reciepentAddress}

object Main extends App {
//  val pdf = Report.render("/FORMS/Test", "PDF", new util.HashMap[String, String]())
//  print(pdf)
  val pgpMail = new PGPMail(executablePath, new DefaultAuthenticator(fromAddress, passphrase), Array(reciepentAddress))
  val props = System.getProperties()
  props.put("mail.smtp.host", "webmail.nagaworld.com")
  props.put("mail.smtp.port", "25")
  val session = Session.getDefaultInstance(props, null)
  val message = new MimeMessage(session)
  message.setFrom(new InternetAddress("aml@nagaworld.com"))
  message.setRecipient(Message.RecipientType.TO, new InternetAddress("imsopheap@nagaworld.com"))
  message.setSubject("Program Testing")
  message.setSentDate(new Date())
  val messagePart = new MimeBodyPart()
  messagePart.setText("hello world")
  val multipart = new MimeMultipart()
  multipart.addBodyPart(messagePart)
  val encrypted = pgpMail.signedAndEncrypted(multipart)
  message.setContent(encrypted)
  Transport.send(message)
}
