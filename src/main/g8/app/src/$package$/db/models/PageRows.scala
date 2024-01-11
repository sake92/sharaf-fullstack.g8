package $package$.db.models

case class PageRows[T](rows: Seq[T], total: Int)
