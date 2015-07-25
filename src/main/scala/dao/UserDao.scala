package dao

import dto.UserDto
import scalikejdbc.DBSession

trait UserDao {
  def insert(user: UserDto)(implicit session: DBSession): Int

  def delete(userId: Int)(implicit session: DBSession): Int

  def find(userId: Int)(implicit session: DBSession): Option[UserDto]

  def list(maxCount: Int, minId: Int)(implicit session: DBSession): List[UserDto]
}

trait UserDaoComponent {
  val userDao: UserDao
}
