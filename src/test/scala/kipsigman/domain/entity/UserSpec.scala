package kipsigman.domain.entity

import org.scalatest.Matchers
import org.scalatest.WordSpec

class UserSpec extends WordSpec with Matchers with TestData {    
  "hasRole" should {
    "return true if Role is in Set" in {
      user.copy(roles = Set(Role.Editor)).hasRole(Role.Editor) shouldBe true
      user.copy(roles = Set(Role.Editor, Role.Member)).hasRole(Role.Member) shouldBe true
    }
    
    "return false if Role is not in Set" in {
      user.hasRole(Role.Editor) shouldBe false
      user.hasRole(Role.Administrator) shouldBe false
      user.copy(roles = Set(Role.Editor)).hasRole(Role.Member) shouldBe false
      user.copy(roles = Set(Role.Editor, Role.Member)).hasRole(Role.Administrator) shouldBe false
    }
    
    "return true for all Roles if Administrator Role exists" in {
      user.copy(roles = Set(Role.Administrator)).hasRole(Role.Administrator) shouldBe true
      user.copy(roles = Set(Role.Administrator)).hasRole(Role.Editor) shouldBe true
      user.copy(roles = Set(Role.Administrator, Role.Editor)).hasRole(Role.Member) shouldBe true
    }
  }
}