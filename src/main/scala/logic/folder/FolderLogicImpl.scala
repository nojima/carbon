package logic.folder

import dto.FolderDto
import model.{Folders => FolderTable, Folder => FolderModel}
import scala.slick.driver.H2Driver.simple._

class FolderLogicImpl(db: Database) extends FolderLogic {
  private val folderQuery = TableQuery[FolderTable]

  override def insert(folder: FolderDto): Int =
    db.withTransaction { implicit session =>
      (folderQuery returning folderQuery.map(_.id)) += FolderModel(-1, folder.owner, folder.name)
    }

  override def delete(folderId: Int) =
    db.withTransaction { implicit session =>
      folderQuery.filter(_.id === folderId).delete
    }

  override def find(folderId: Int): Option[FolderDto] =
    db.withTransaction { implicit session =>
      folderQuery.filter(_.id === folderId).firstOption
        .flatMap(x => Some(new FolderDto(x.id, x.owner, x.name)))
    }
}
