package kipsigman.domain.repository.slick

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import kipsigman.domain.entity.IdEntity
import kipsigman.domain.entity.LifecycleEntity
import kipsigman.domain.entity.Page
import kipsigman.domain.entity.PageFilter
import kipsigman.domain.entity.Status

trait SlickRepository extends TableGroupConfig {
  import driver.api._
  
  implicit protected def ec: ExecutionContext
  
  protected def pageQuery[T <: IdEntity](query: Query[IdTable[T], T, Seq], pageFilter: PageFilter): Future[Page[T]] = {
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
  
  protected def pageResults[T <: IdEntity](itemsFuture: Future[Seq[T]], pageFilter: PageFilter): Future[Page[T]] = {
    itemsFuture.map(allItems => {
      val items = allItems.drop(pageFilter.offset).take(pageFilter.pageSize + 1)
      if (items.size > pageFilter.pageSize) {
        Page(items.dropRight(1), pageFilter, true)
      } else {
        Page(items, pageFilter, false)
      }
    })
  }
  
  protected def filterActive[T <: LifecycleEntity[T]](entities: Seq[T]): Seq[T] = {
    entities.filter(entity => Status.activeValues.contains(entity.status))
  }
  
  protected def filterActive[T <: LifecycleEntity[T]](entitiesFuture: Future[Seq[T]]): Future[Seq[T]] = {
    entitiesFuture.map(entities => filterActive(entities))
  }
}