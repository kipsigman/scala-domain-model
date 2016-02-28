package kipsigman.domain.entity

trait LifecycleEntity[T <: LifecycleEntity[T]] extends IdEntity {
  def status: Status
  
  protected def updateStatusCopy(newStatus: Status): T
}

abstract class Status(val name: String) {
  override def toString: String = name
}

object Status {
  case object Deleted extends Status("deleted")
  case object Draft extends Status("draft")
  case object Public extends Status("public")
  case object Unlisted extends Status("unlisted")
  
  val all: Set[Status] = Set(Deleted, Draft, Public, Unlisted)
  val activeValues: Set[Status] = Set(Draft, Public, Unlisted)
  val publishValues: Set[Status] = Set(Public, Unlisted)
  
  def apply(name: String): Status = {
    all.find(s => s.name == name) match {
      case Some(status) => status
      case None => throw new IllegalArgumentException(s"Invalid Status: $name")
    }
  }
}

class IllegalStatusChangeException(oldStatus: Status, newStatus: Status) 
  extends RuntimeException(s"Illegal status change: ${oldStatus.name} -> ${newStatus.name}")