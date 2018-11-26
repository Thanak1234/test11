package libs

import com.typesafe.config.ConfigFactory

object MailConfig {
  val conf = ConfigFactory.load()
  val webmailUrl = conf.getString("mail.serviceUrl")
  val port = conf.getString("mail.port")
}
