package extensions

import slick.lifted.Rep
import slick.ast.Ordering.Direction
import slick.ast.Ordering
import slick.lifted.Query
import slick.lifted.ColumnOrdered
import slick.lifted.Ordered

import scala.util.control.NoStackTrace

case class PageRequest(offset: Int,
                       limit: Int,
                       sort: Vector[(String, Direction)])

// Based off https://stackoverflow.com/questions/42383842/dynamic-order-by-in-scala-slick-with-several-columns
object DynamicSortBySupport {

  case class InvalidSort(invalidField: String)
    extends Exception with NoStackTrace

  trait HasSortColumns {

    /** The runtime map between string names and table columns */
    val sortColumns: Map[String, Rep[_]]
  }
  implicit class MultiSortableQuery[E <: HasSortColumns, U, C[_]](
                                                                   query: Query[E, U, C]) {

    /** Sorts the query by multiple columns in order. Invalid sort names are skipped */
    def dynamicSortBy(sortBy: Seq[(String, Direction)])
    : Either[InvalidSort, Query[E, U, C]] = {
      val zero: Either[InvalidSort, Query[E, U, C]] = Right(query)
      sortBy.foldRight(zero) {
        // foldRight specifically because stacking sortBy invocations on a query operates in reverse order,
        // so the reversed order of a right fold means the final result is in the desired order.
        case (_, err @ Left(_)) => err
        case ((sortColumn, sortOrder), Right(queryToSort)) =>
          val sortOrderRep: Rep[_] => Ordered =
            ColumnOrdered(_, Ordering(sortOrder))
          val sortColumnRep: E => Rep[_] =
            _.sortColumns.getOrElse(sortColumn, throw InvalidSort(sortColumn))
          try {
            Right(queryToSort.sortBy(sortColumnRep)(sortOrderRep))
          } catch {
            case ex: InvalidSort => Left(ex)
          }
      }
    }

    def paginated(paging: PageRequest): Either[InvalidSort, Query[E, U, C]] =
      dynamicSortBy(paging.sort).map(q =>
        q.drop(paging.offset).take(paging.limit))
  }
}