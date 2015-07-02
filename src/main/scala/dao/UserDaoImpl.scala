package dao

import dto.UserDto
import scalikejdbc._

class UserDaoImpl extends UserDao {
  def insert(user: UserDto)(implicit session: DBSession): Int =
    sql"""INSERT INTO "users" ("id", "name", "password") VALUES (${user.id}, ${user.name}, ${user.password})""".update().apply()

  def delete(userId: Int)(implicit session: DBSession): Int =
    sql"""DELETE FROM "users" WHERE "id" = $userId""".update().apply()

  def find(userId: Int)(implicit session: DBSession): Option[UserDto] =
    sql"""SELECT "id", "name", "password" FROM "users" WHERE "id" = $userId"""
      .map(toUserDto).single().apply()

  def list(maxCount: Int, minId: Int)(implicit session: DBSession): List[UserDto] =
    sql"""SELECT "id", "name", "password" FROM "users" WHERE "id" >= $minId ORDER BY "id" LIMIT $maxCount"""
      .map(toUserDto).list().apply()

  private def toUserDto(r: WrappedResultSet) =
    UserDto(r.int("id"), r.string("name"), r.string("password"))
}
