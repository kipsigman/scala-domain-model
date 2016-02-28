package kipsigman.domain.entity

trait UserOwnedEntity extends IdEntity {
  def userIdOption: Option[Int]
  
  final def isOwnedBy(userOption: Option[User]): Boolean = (userIdOption, userOption) match {
    case (Some(definedUserId), Some(user)) => user.isId(definedUserId)
    case _ => false
  }
}