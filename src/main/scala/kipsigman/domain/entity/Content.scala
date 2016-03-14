package kipsigman.domain.entity

trait Content[T <: Content[T]] extends LifecycleEntity[T] with UserOwnedEntity {
  import Content._
  
  def status: Status
  
  def contentClass: ContentClass
  
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