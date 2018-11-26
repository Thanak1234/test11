package utils

import play.api.libs.json.Json
import play.api.mvc.QueryStringBindable

final case class Sort(property: Option[String]=None, direction: Option[String]=None)
object Sort {
  implicit val format = Json.format[Sort]

  implicit def queryStringBinder(
                                  implicit sb: QueryStringBindable[String],
                                ) = new QueryStringBindable[Sort] {
    private def subBind[T](key: String, subkey: String, params: Map[String, Seq[String]])(implicit b: QueryStringBindable[T]): Either.RightProjection[String, Option[T]] = {
      b.bind(s"$subkey", params).map(_.right.map(r => Option(r))).getOrElse(Right(None)).right
    }

    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Sort]] = Some {
      def bnd[T](s: String)(implicit b: QueryStringBindable[T]) = subBind[T](key, s, params)
      for {
        property <- bnd[String]("property")
        direction <- bnd[String]("direction")
      } yield Sort(property, direction)
    }

    override def unbind(key: String, o: Sort): String = {
      def ubnd[T](key: String, s: Option[T])(implicit b: QueryStringBindable[T]) = s.map(f => b.unbind(key, f))
      val keys = Seq(
        ubnd("property", o.property),
        ubnd("direction", o.direction)
      ).flatten
      keys.mkString("&")
    }
  }
}
