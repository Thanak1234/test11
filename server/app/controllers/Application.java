package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.typesafe.config.ConfigFactory;

public class Application extends Controller {
  
  public Result index() {
    return ok(index.render("AML (Dev)"));
  }
  
}