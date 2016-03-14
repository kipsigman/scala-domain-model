package kipsigman.domain.entity

trait LifecycleEntity[T <: LifecycleEntity[T]] extends IdEntity {
  def status: Status
  
  protected def updateStatusCopy(newStatus: Status): T
  
  final def isDeleted: Boolean = status == Status.Deleted
  final def isDraft: Boolean = status == Status.Draft
  final def isPublic: Boolean = status == Status.Public
  final def isUnlisted: Boolean = status == Status.Unlisted
  final def isPublished: Boolean = Status.publishValues.contains(status)
}

class IllegalStatusChangeException(oldStatus: Status, newStatus: Status) 
  extends RuntimeException(s"Illegal status change: ${oldStatus.name} -> ${newStatus.name}")