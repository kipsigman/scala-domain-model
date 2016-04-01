package kipsigman.domain.entity

abstract class Status(val name: String) {
  override def toString: String = name
}

object Status {
  // Content
  case object Deleted extends Status("deleted")
  case object Draft extends Status("draft")
  case object Public extends Status("public")
  case object Unlisted extends Status("unlisted")
  
  // Reviewed entity
  case object Approved extends Status("approved")
  case object Pending extends Status("pending")
  case object Rejected extends Status("rejected")
  
  val all: Set[Status] = Set(Deleted, Draft, Public, Unlisted, Approved, Pending, Rejected)
  val activeValues: Set[Status] = Set(Draft, Public, Unlisted, Approved, Pending)
  val publishValues: Set[Status] = Set(Public, Unlisted)
  
  def apply(name: String): Status = {
    all.find(s => s.name == name) match {
      case Some(status) => status
      case None => throw new IllegalArgumentException(s"Invalid Status: $name")
    }
  }
}