package services

import dao.UserDaoComponent
import dto.UserDto
import lib.Database

class UserService(db: Database) { this: UserDaoComponent =>
  def addUser(user: UserDto): Int = {
    if (user.name.isEmpty)
      throw new IllegalArgumentException("Username cannot be empty.")
    if (user.password.isEmpty)
      throw new IllegalArgumentException("Password cannot be empty.")
    db.transaction { implicit session =>
      userDao.insert(user)
    }
  }

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
