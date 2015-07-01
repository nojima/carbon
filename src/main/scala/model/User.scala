package model

import scala.slick.driver.H2Driver.simple._

case class User(
  id: Int,
  name: String,
  password: String
)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def password = column[String]("password")

  def * = (id, name, password) <> (User.tupled, User.unapply)
}
