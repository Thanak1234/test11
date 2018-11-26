package utils

import play.api.mvc.QueryStringBindable

case class FilterSort(var start: Option[Int], var limit: Option[Int], var query: Option[String], filter: Option[String], sort: Option[String])

object FilterSort {
  implicit def queryStringBinder(
                                  implicit ib: QueryStringBindable[Int],
                                  sb: QueryStringBindable[String],
                                  sob: QueryStringBindable[Sort],
                                  fb: QueryStringBindable[Seq[Filter]],
                                ) = new QueryStringBindable[FilterSort] {
    private def subBind[T](key: String, subkey: String, params: Map[String, Seq[String]])(implicit b: QueryStringBindable[T]): Either.RightProjection[String, Option[T]] = {
      b.bind(s"$subkey", params).map(_.right.map(r => Option(r))).getOrElse(Right(None)).right
    }

    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, FilterSort]] = Some {
      def bnd[T](s: String)(implicit b: QueryStringBindable[T]) = subBind[T](key, s, params)
      for {
        start <- bnd[Int]("start")
        limit <- bnd[Int]("limit")
        query <- bnd[String]("query")
        filter <- bnd[String]("filter")
        sort <- bnd[String]("sort")
      } yield FilterSort(start, limit, query, filter, sort)
    }

    override def unbind(key: String, page: FilterSort): String = {
      def ubnd[T](key: String, s: Option[T])(implicit b: QueryStringBindable[T]) = s.map(f => b.unbind(key, f))
      val keys = Seq(
        ubnd("start", page.start),
        ubnd("limit", page.limit),
        ubnd("query", page.query),
        ubnd("filter", page.filter),
        ubnd("sort", page.sort)
      ).flatten
      keys.mkString("&")
    }
  }
}