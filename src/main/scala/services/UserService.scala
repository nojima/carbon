package services

import dao.UserDaoComponent
import dto.UserDto
import scalikejdbc._

class UserService { this: UserDaoComponent =>
  def addUser(user: UserDto): Int = {
    if (user.name.isEmpty)
      throw new IllegalArgumentException("Username cannot be empty.")
    if (user.password.isEmpty)
      throw new IllegalArgumentException("Password cannot be empty.")
    DB localTx { implicit session =>
      userDao.insert(user)
    }
  }

  def findUser(userId: Int): Option[UserDto] =
    DB readOnly { implicit session =>
      userDao.find(userId)
    }

  def listUsers(): List[UserDto] =
    DB readOnly { implicit session =>
      // TODO: ページャを実装する
      userDao.list(100, 0)
    }
}
