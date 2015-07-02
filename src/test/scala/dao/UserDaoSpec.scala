package dao

import dto.UserDto
import lib.SessionSupport
import org.scalatest.FunSpec

class UserDaoSpec extends FunSpec with SessionSupport {
  val sut: UserDao = new UserDaoImpl

  describe("UserDaoは") {
    it("insertしてfindできる") {
      withSession { implicit session =>
        // Setup
        val dto = UserDto(0, "aaa", "bbb")

        // Exercise
        sut.insert(dto)

        // Verify
        val actual = sut.find(1).get
        assert(actual.name == dto.name)
        assert(actual.password == dto.password)
      }
    }

    it("insertしてdeleteできる") {
      withSession { implicit session =>
        // Setup
        sut.insert(UserDto(0, "ccc", "ddd"))

        // Exercise
        sut.delete(1)

        // Verify
        assert(sut.find(1).isEmpty)
      }
    }

    it("insertしてlistできる") {
      withSession { implicit session =>
        // Setup
        sut.insert(UserDto(0, "eee", "fff"))
        sut.insert(UserDto(0, "ggg", "hhh"))

        // Exercise
        val users = sut.list(2, 0)

        // Verify
        assert(users.length == 2)
        assert(users(0) == UserDto(1, "eee", "fff"))
        assert(users(1) == UserDto(2, "ggg", "hhh"))
      }
    }
  }
}
