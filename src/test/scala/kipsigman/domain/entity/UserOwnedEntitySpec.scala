package kipsigman.domain.entity

import org.scalatest.Matchers
import org.scalatest.WordSpec

class UserOwnedEntitySpec extends WordSpec with Matchers with TestData {
  val userOwnedEntity: UserOwnedEntity = new UserOwnedEntity {
    override val id = Option(1)
    override val userIdOption = user.id
  }
  val anonymousEntity: UserOwnedEntity = new UserOwnedEntity {
    override val id = Option(1)
    override val userIdOption = None
  }
  
  "isOwnedBy" should {
    "return true if userId is defined and matches user" in {
      userOwnedEntity.userIdOption.isDefined shouldBe true
      user.id.isDefined shouldBe true
      userOwnedEntity.userIdOption shouldBe user.id
      userOwnedEntity.isOwnedBy(Option(user)) shouldBe true
    }
    "return false if userId is defined but does not match user" in {
      userOwnedEntity.userIdOption.isDefined shouldBe true
      user2.id.isDefined shouldBe true
      userOwnedEntity.isOwnedBy(Option(user2)) shouldBe false
    }
    "return false if userId is not defined" in {
      anonymousEntity.userIdOption shouldBe None
      anonymousEntity.isOwnedBy(Option(user)) shouldBe false
    }
  }
}