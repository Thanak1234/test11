package libs

import de.buelowssiege.mail.pgp_mime.gpg.{GnuPGBodyPartEncrypter, GnuPGBodyPartSigner}
import de.buelowssiege.mail.pgp_mime.{MimeMultipartEncrypted, MimeMultipartSigned, PGPAuthenticator}
import javax.mail.internet.MimeMultipart

class PGPMail(GPG_EXECUTABLE: String, authenticator: PGPAuthenticator, recipients: Array[String]) {

  def signed(multiParts: MimeMultipart) = {
    val signer = new GnuPGBodyPartSigner(GPG_EXECUTABLE, authenticator)
    val messageSign = MimeMultipartSigned.createInstance(multiParts, signer)
    messageSign.verify(signer)
    messageSign
  }

  def encrypted(multiParts: MimeMultipart) = {
    val encrypter = new GnuPGBodyPartEncrypter(GPG_EXECUTABLE, authenticator, recipients)
    MimeMultipartEncrypted.createInstance(multiParts, encrypter)
  }

  def signedAndEncrypted(multiParts: MimeMultipart) = encrypted(signed(multiParts))
}

object PGPMail