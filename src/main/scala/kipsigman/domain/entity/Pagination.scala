package kipsigman.domain.entity

case class PageFilter(page: Int = 0, pageSize: Int = 20) {
  val offset = page * pageSize
}

case class Page[T](items: Seq[T], pageFilter: PageFilter, hasNext: Boolean) {
  lazy val current: Int = pageFilter.page
  lazy val prev: Option[Int] = Option(current - 1).filter(_ >= 0)
  lazy val next: Option[Int] = Option(current + 1).filter(_ => hasNext)
  lazy val from: Int = pageFilter.offset + 1
  lazy val to: Int = pageFilter.offset + items.size
  
  lazy val currentDisplay: Int = current + 1
  lazy val prevDisplay: Option[Int] = prev.map(_ + 1)
  lazy val nextDisplay: Option[Int] = next.map(_ + 1)
}