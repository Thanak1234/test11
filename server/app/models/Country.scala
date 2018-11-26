package models

import java.util.UUID

import extensions.CustomColumnTypes._
import org.joda.time.DateTime
import play.api.libs.json.Json
import slick.jdbc.SQLServerProfile.api._

final case class Country (
   id: Option[UUID],
   cmpId: Option[String],
   cmpCode: Option[String],
   bankCode: Option[String],
   isoCode: Option[String],
   name: Option[String],
   status: Option[String],
)

object Country {
  implicit val format = Json.format[Country]
}

final class CountryTable (tag: Tag) extends Table[Country](tag, Some("reusable"), "country") {
  def id = column[Option[UUID]]("id")
  def cmpId = column[Option[String]]("cmpId")
  def cmpCode = column[Option[String]]("cmpCode")
  def bankCode = column[Option[String]]("bankCode")
  def isoCode = column[Option[String]]("isoCode")
  def name = column[Option[String]]("name")
  def status = column[Option[String]]("status")
  def * = (id, cmpId, cmpCode, bankCode, isoCode, name, status) <> ((Country.apply _).tupled, Country.unapply)
}


