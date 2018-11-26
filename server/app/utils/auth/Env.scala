package utils.auth

import auth.models.IdentityUser
import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators._


trait JWTEnv extends Env {
  type I = IdentityUser
  type A = JWTAuthenticator
}