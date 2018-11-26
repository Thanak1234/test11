package dto

import play.api.data.Form
import play.api.data.Forms._

object LoginInfo {

  /**
    * A play framework form.
    */
  val form = Form(
    mapping(
      "user" -> email,
      "password" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  /**
    * The form data.
    *
    * @param email The email of the user.
    * @param password The password of the user.
    * @param rememberMe Indicates if the user should stay logged in on the next visit.
    */
  case class Data(
                   user: String,
                   password: String)
}
