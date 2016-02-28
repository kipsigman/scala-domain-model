package kipsigman.domain.entity

trait Content[T <: Content[T]] extends LifecycleEntity[T] with UserOwnedEntity {
  import Content._
  
  def status: Status
  
  def contentClass: ContentClass
  
  final def isDeleted: Boolean = status == Status.Deleted
  final def isDraft: Boolean = status == Status.Draft
  final def isPublic: Boolean = status == Status.Public
  final def isUnlisted: Boolean = status == Status.Unlisted
  final def isPublished: Boolean = Status.publishValues.contains(status)
  
  final def updateStatus(newStatus: Status)(implicit user: Option[User]): T = {
    val statusChangeOk = (status, newStatus) match {
      case (Status.Deleted, _) => false 
      case (_, Status.Public) => user.map(_.isEditor).getOrElse(false)
      case (_, _) => true
    }
    
    if (statusChangeOk) {
      updateStatusCopy(newStatus)   
    } else {
      throw new IllegalStatusChangeException(status, newStatus)
    }
  }
}

object Content {
  class ContentClass(val underlying: String) extends AnyVal {
    override def toString: String = underlying
  }
}