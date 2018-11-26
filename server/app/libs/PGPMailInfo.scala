package libs

import javax.mail.Session

case class PGPMailInfo(subject: String, bodyMessage: String, headers: Seq[(String, String)], attachments: Seq[AttachmentResource])
