package kipsigman.domain.repository

import kipsigman.domain.entity.IdEntity

case class PageFilter(page: Int = 0, pageSize: Int = 20) {
  val offset = page * pageSize
}

case class Page[T <: IdEntity](items: Seq[T], pageFilter: PageFilter, hasNext: Boolean) {
  lazy val prev = Option(pageFilter.page - 1).filter(_ >= 0)
  lazy val next = Option(pageFilter.page + 1).filter(_ => hasNext)
  lazy val from = pageFilter.offset + 1
  lazy val to = pageFilter.offset + items.size
}