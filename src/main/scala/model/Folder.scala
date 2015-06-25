package model

import slick.driver.H2Driver.api._

case class Folder(
  id: Int,
  owner: String,
  name: String
)

class Folders(tag: Tag) extends Table[Folder](tag, "folders") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def owner = column[String]("owner")
  def name = column[String]("name")

  def path = index("path_folder", (owner, name), unique = true)

  def * = (id, owner, name) <> (Folder.tupled, Folder.unapply)
}
