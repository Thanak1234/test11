package utils.auth

import com.unboundid.ldap.sdk._
import java.security.MessageDigest

import LdapConf._
import javax.inject.{Inject, Singleton}

trait LdapUtil {
  def authenticate(uid:String, pass:String) : ResultCode
}

@Singleton
class LdapUtilImpl @Inject() () extends LdapUtil {
  val ldapConnection = new LDAPConnection()

  initConnection()

  def initConnection()= {
    val options = new LDAPConnectionOptions()
    options.setAbandonOnTimeout(true)
    options.setConnectTimeoutMillis(5000)
    options.setAutoReconnect(true)
    options.setUseTCPNoDelay(true)
    ldapConnection.setConnectionOptions(options)
    ldapConnection.connect(ldapHost, ldapPort)
  }

  def authenticate(uid:String, pass:String) : ResultCode = {
    val msg : String = uid + pass + "ela_salt_201406"
    val hash : String =  MessageDigest.getInstance("SHA-256")
      .digest(msg.getBytes)
      .foldLeft("")(
        (s: String, b: Byte) =>
          s + Character.forDigit((b & 0xf0) >> 4, 16) + Character.forDigit(b & 0x0f, 16)
      )
    val cacheKey = "bindResult." + hash
    val bindResult = try {
      ldapConnection.bind(uid, pass).getResultCode
    } catch {
      case _: Throwable => ResultCode.CONNECT_ERROR
    }

    bindResult
  }
}
