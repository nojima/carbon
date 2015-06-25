package dao

import dto.FolderDto
import model.{Folders => FolderTable, Folder => FolderModel}
import util.DatabaseUtil

import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

class FolderDaoImpl(db: Database) extends FolderDao {
  private val folderQuery = TableQuery[FolderTable]

  override def insert(folder: FolderDto): Int = {
    val action = (folderQuery returning folderQuery.map(_.id)) += FolderModel(-1, folder.owner, folder.name)
    DatabaseUtil.runAction(db, action)
  }

  override def delete(folderId: Int) = {
    val action = folderQuery.filter(_.id === folderId).delete
    DatabaseUtil.runAction(db, action)
  }

  override def find(folderId: Int): Option[FolderDto] = {
    val action = {
      val optFolderAction = folderQuery.filter(_.id === folderId).result.headOption
      optFolderAction.map(opt => opt.map(f => new FolderDto(f.id, f.owner, f.name)))
    }
    DatabaseUtil.runAction(db, action)
  }
}
