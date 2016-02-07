package kipsigman.domain.repository.slick

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import kipsigman.domain.entity.IdEntity
import kipsigman.domain.repository.Page
import kipsigman.domain.repository.PageFilter

trait SlickRepository extends TableGroupConfig {
  import driver.api._
  
  implicit protected def ec: ExecutionContext
  
  private[slick] def pageQuery[T <: IdEntity](query: Query[IdTable[T], T, Seq], pageFilter: PageFilter): Future[Page[T]] = {
    // Query pageSize + 1 to determine if there are more results after this page
    val itemsQuery = query.drop(pageFilter.offset).take(pageFilter.pageSize + 1)
    val itemsFuture = db.run(itemsQuery.result)

    itemsFuture.map(items => {
      if (items.size > pageFilter.pageSize) {
        Page(items.dropRight(1), pageFilter, true)
      } else {
        Page(items, pageFilter, false)
      }
    })
  }
}