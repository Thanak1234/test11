package libs

import de.buelowssiege.mail.pgp_mime.PGPAuthenticator

class DefaultAuthenticator(localUser: String, passphrase: String) extends PGPAuthenticator {

  override def getLocalUser: String = localUser

  override def useDefaultLocalUser(): Boolean = false

  override def getPassphrase: Array[Char] = {
    val pass = passphrase
    val passPhrase: Array[Char] = new Array[Char](pass.length)
    pass.getChars(0, pass.length, passPhrase, 0)
    passPhrase
  }
}
