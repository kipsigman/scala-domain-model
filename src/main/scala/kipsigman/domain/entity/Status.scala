package kipsigman.domain.entity

import play.api.data.validation.ValidationError
import play.api.libs.json._

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
  
  implicit val reads: Reads[Status] = new Reads[Status] {
    def reads(json: JsValue) = json match {
      case JsString(s) => JsSuccess(Status(s))
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.jsstring"))))
    }
  }
  
  implicit val writes: Writes[Status] = new Writes[Status] {
    def writes(status: Status) = JsString(status.name)
  }
}