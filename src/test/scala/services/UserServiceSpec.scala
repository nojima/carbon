package services

import dto.UserDto
import lib.FakeDatabaseImpl
import org.mockito.Mockito._
import org.mockito.Matchers.{any, eq => equal}

import dao.{UserDaoComponent, UserDao}
import org.scalatest.{BeforeAndAfter, FunSpec}
import scalikejdbc.DBSession

class UserServiceSpec extends FunSpec with BeforeAndAfter {
  var sut: UserService = _
  var userDaoMock: UserDao = _

  before {
    userDaoMock = mock(classOf[UserDao])
    sut =
      new UserService(new FakeDatabaseImpl) with UserDaoComponent {
      val userDao = userDaoMock
    }
  }

  describe("addUserは") {
    it("insertできる") {
      // Setup
      val dto = UserDto(0, "name", "password")

      // Exercise
      sut.addUser(dto)

      // Verify
      verify(userDaoMock).insert(equal(dto))(any[DBSession])
    }

    it("nameが空の場合に例外を投げる") {
      intercept[IllegalArgumentException] {
        sut.addUser(UserDto(0, "", "password"))
      }
    }

    it("passwordが空の場合に例外を投げる") {
      intercept[IllegalArgumentException] {
        sut.addUser(UserDto(0, "name", ""))
      }
    }

    describe("listUserは") {
      it("userを列挙する") {
        // Exercise
        sut.listUsers()

        // Verify
        verify(userDaoMock).list(any[Int], any[Int])(any[DBSession])
      }
    }

    describe("findUserは") {
      it("userを取ってこれる") {
        // Exercise
        sut.findUser(1)

        // Verify
        verify(userDaoMock).find(equal(1))(any[DBSession])
      }
    }
  }
}
