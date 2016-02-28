package kipsigman.domain.entity

trait TestData {
  val user = UserBasic(Option(66), Option("Johnny"), Option("Utah"), "johnny.utah@fbi.gov", None, Set(Role.Member))
  implicit val userOption: Option[User] = Option(user)
  val editor: Option[User] = Option(user.copy(roles = user.roles + Role.Editor))
  
  val user2 = UserBasic(Option(67), Option("Angelo"), Option("Pappas"), "angelo.pappas@fbi.gov", None, Set())
}