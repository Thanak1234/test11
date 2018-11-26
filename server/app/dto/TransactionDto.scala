package dto

import java.util.UUID

import play.api.libs.json.Json
import slick.jdbc.GetResult
import extensions.SQLExtension._

final case class TransactionDto(
    id: Option[UUID],
    playerId: Option[String],
    playerName: Option[String],
    driverLicense: Option[String],
    gercCardNo: Option[String],
    idCard: Option[String],
    passport: Option[String],
    playerCountry: Option[String],
    categoryName: Option[String],
    totalAmount: Option[Float],
    noTrans: Option[Int],
    transType: Option[String],
    frmIds: Option[String]
)

object TransactionDto {
  implicit val format = Json.format[TransactionDto]
  implicit val getCtrTransactionResult = GetResult(r => TransactionDto(
    r.nextUUIDOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextStringOption(),
    r.nextFloatOption(),
    r.nextIntOption(),
    r.nextStringOption(),
    r.nextStringOption()
  ))
}