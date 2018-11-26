package libs

import com.typesafe.config.ConfigFactory

object PGPConfig {
  val conf = ConfigFactory.load()
  val executablePath = conf.getString("pgp.executablePath")
  val fromAddress = conf.getString("pgp.fromAddress")
  val reciepentAddress = conf.getString("pgp.reciepentAddress")
  val passphrase = conf.getString("pgp.passphrase")
}
