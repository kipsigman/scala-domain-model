package kipsigman.domain.entity

trait IdEntity {
  def id: Option[Int]
  
  final def isPersisted: Boolean = id.isDefined
  
  final def isId(otherId: Int): Boolean = id.map(_ == otherId).getOrElse(false)
}