package controllers

import java.io.File

import javax.inject.Inject
import play.Play
import play.api.mvc.{Action, AnyContent, Controller}
import play.mvc.FileMimeTypes
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global

class Sencha @Inject()(implicit fileMimeTypes: FileMimeTypes) extends Controller {

  def at(rootPath: String, file: String): Action[AnyContent] = Action {
    var f = new File(Play.application.getFile(rootPath), file)
    val fileName = f.getName
    val index = fileName.indexOf(".")
    val ext = f.getName.substring(index + 1, fileName.length)
    var contentType = "text/javascript"
    if(ext == "js") {
      contentType = "text/javascript"
    } else if(ext == "png") {
      contentType = "image/png"
    } else if(ext == "json") {
      contentType = "application/json"
    } else if(ext == "jpeg" || ext == "jpg") {
      contentType = "image/jpeg"
    } else if(ext == "css") {
      contentType = "text/css"
    } else if(ext == "gif") {
      contentType = "image/gif"
    } else if(ext == "ttf") {
      contentType = "application/x-font-ttf"
    } else if(ext == "woff") {
      contentType = "application/x-font-woff"
    }

    if (f.exists())
      Ok(scala.io.Source.fromFile(f.getCanonicalPath(), "UTF-8").mkString).as(contentType)
    else
      NotFound
  }
}
