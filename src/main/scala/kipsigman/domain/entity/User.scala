package kipsigman.domain.entity

import scala.annotation.implicitNotFound

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait User extends IdEntity {
  def firstName: Option[String]
  def lastName: Option[String]
  def email: String
  def avatarURL: Option[String]
  def roles: Set[Role]
  
  final lazy val name: String = firstName.getOrElse("") + lastName.map(ln => s" $ln").getOrElse("")
  
  /**
   * Checks for Role. Administrators are assumed to have all roles.
   */
  final def hasRole(role: Role): Boolean = roles.contains(role) || roles.contains(Role.Administrator)
  
  final lazy val isAdministrator: Boolean = hasRole(Role.Administrator)
  final lazy val isEditor: Boolean = hasRole(Role.Editor)
  final lazy val isMember: Boolean = hasRole(Role.Member)
}

case class UserBasic(
  id: Option[Int],
  firstName: Option[String],
  lastName: Option[String],
  email: String,
  avatarURL: Option[String],
  roles: Set[Role] = Set(Role.Member)) extends User
  
object UserBasic {
  implicit val reads: Reads[UserBasic] = (
    (JsPath \ "id").readNullable[Int] and
    (JsPath \ "firstName").readNullable[String] and
    (JsPath \ "lastName").readNullable[String] and
    (JsPath \ "email").read[String] and
    (JsPath \ "avatarURL").readNullable[String] and
    (JsPath \ "roles").read[Set[Role]]
  )(UserBasic.apply _)
  
  implicit val writes: Writes[UserBasic] = (
    (JsPath \ "id").writeNullable[Int] and
    (JsPath \ "firstName").writeNullable[String] and
    (JsPath \ "lastName").writeNullable[String] and
    (JsPath \ "email").write[String] and
    (JsPath \ "avatarURL").writeNullable[String] and
    (JsPath \ "roles").write[Set[Role]]
  )(unlift(UserBasic.unapply))
  
}

sealed abstract class Role(val name: String) {
  override def toString: String = name
}

object Role {
  // Site admin
  case object Administrator extends Role("administrator")
  // Can edit ArticleTemplates
  case object Editor extends Role("editor")
  // Can customize and manage Articles
  case object Member extends Role("member")
  
  val all: Set[Role] = Set(Administrator, Editor, Member)
  
  def apply(name: String): Role = {
    all.find(s => s.name == name) match {
      case Some(role) => role
      case None => throw new IllegalArgumentException(s"Invalid Role: $name")
    }
  }
  
  implicit val reads: Reads[Role] = new Reads[Role] {
    def reads(json: JsValue) = json match {
      case JsString(s) => JsSuccess(Role(s))
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.jsstring"))))
    }
  }
  
  implicit val writes: Writes[Role] = new Writes[Role] {
    def writes(role: Role) = JsString(role.name)
  }
}
