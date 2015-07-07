package services

import dao.UserDaoComponent
import dto.UserDto
import lib.Database

import scala.util.{Success, Failure, Try}

class UserService(db: Database) { this: UserDaoComponent =>
  def validateUser(user: UserDto): Try[Unit] = {
    if (user.name.isEmpty)
      return Failure(new IllegalArgumentException("username cannot be empty."))
    if (user.password.isEmpty)
      return Failure(new IllegalArgumentException("password cannot be empty."))
    Success(())
  }

  def forceAddUser(user: UserDto): Try[Int] =
    Try {
      db.transaction { implicit session =>
        userDao.insert(user)
      }
    }

  def addUser(user: UserDto): Try[Int] =
    validateUser(user).flatMap(_ => forceAddUser(user))

  def findUser(userId: Int): Option[UserDto] =
    db.transaction { implicit session =>
      userDao.find(userId)
    }

  def listUsers(): List[UserDto] =
    db.transaction { implicit session =>
      // TODO: ページャを実装する
      userDao.list(100, 0)
    }
}
