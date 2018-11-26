package libs

import javax.mail.Message.RecipientType

case class Recipient (name: String, address: String, recipientType: RecipientType)
