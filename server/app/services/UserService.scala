package com.aml.services

import auth.models.IdentityUser
import com.mohiva.play.silhouette.api.services.IdentityService

trait UserService extends IdentityService[IdentityUser] {

}
