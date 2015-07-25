package dao

import dto.UserDto
import lib.{Database, DatabaseSupport}
import org.scalatest.{BeforeAndAfter, FunSpec}

class UserDaoSpec extends FunSpec with BeforeAndAfter {
  val sut: UserDao = new UserDaoImpl
  var db: Database = null

  before {
    db = DatabaseSupport.createDatabase()
  }

  after {
    DatabaseSupport.destroyDatabase(db)
  }

  describe("UserDaoは") {
    it("insertしてfindできる") {
      // Setup
      val dto = UserDto(0, "aaa", "bbb")

      // Exercise
      db.transaction { implicit session =>
        sut.insert(dto)
      }

      // Verify
      db.transaction { implicit session =>
        val actual = sut.find(1).get
        assert(actual.name == dto.name)
        assert(actual.password == dto.password)
      }
    }

    it("insertしてdeleteできる") {
      // Setup
      db.transaction { implicit session =>
        sut.insert(UserDto(0, "ccc", "ddd"))
      }

      // Exercise
      db.transaction { implicit session =>
        sut.delete(1)
      }

      // Verify
      db.transaction { implicit session =>
        assert(sut.find(1).isEmpty)
      }
    }

    it("insertしてlistできる") {
      // Setup
      db.transaction { implicit session =>
        sut.insert(UserDto(0, "eee", "fff"))
        sut.insert(UserDto(0, "ggg", "hhh"))
      }

      // Exercise
      val users = db.transaction { implicit session =>
        sut.list(2, 0)
      }

      // Verify
      assert(users.length == 2)
      assert(users(0) == UserDto(1, "eee", "fff"))
      assert(users(1) == UserDto(2, "ggg", "hhh"))
    }
  }
}
