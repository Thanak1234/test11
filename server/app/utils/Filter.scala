package utils

import play.api.libs.json.Json
import play.api.mvc.QueryStringBindable

final case class Filter(operator: Option[String], value: Option[String], property: Option[String])

object Filter {
  implicit val format = Json.format[Filter]

  implicit def queryStringBinder(
                                  implicit sb: QueryStringBindable[String],
                                ) = new QueryStringBindable[Filter] {
    private def subBind[T](key: String, subkey: String, params: Map[String, Seq[String]])(implicit b: QueryStringBindable[T]): Either.RightProjection[String, Option[T]] = {
      b.bind(s"$subkey", params).map(_.right.map(r => Option(r))).getOrElse(Right(None)).right
    }

    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Filter]] = Some {
      def bnd[T](s: String)(implicit b: QueryStringBindable[T]) = subBind[T](key, s, params)
      for {
        operator <- bnd[String]("operator")
        value <- bnd[String]("value")
        property <- bnd[String]("property")
      } yield Filter(operator, value, property)
    }

    override def unbind(key: String, o: Filter): String = {
      def ubnd[T](key: String, s: Option[T])(implicit b: QueryStringBindable[T]) = s.map(f => b.unbind(key, f))
      val keys = Seq(
        ubnd("operator", o.operator),
        ubnd("value", o.value),
        ubnd("property", o.property)
      ).flatten
      keys.mkString("&")
    }
  }
}
