package services

import dto.FolderDto
import dao.FolderDaoComponent
import lib.Database

import scala.util.{Success, Failure, Try}

class FolderService(db: Database) { this: FolderDaoComponent =>
  def validateFolder(folder: FolderDto): Try[Unit] = {
    if (folder.owner.isEmpty)
      return Failure(new IllegalArgumentException("owner cannot be empty"))
    if (folder.name.isEmpty)
      return Failure(new IllegalArgumentException("name cannot be empty"))
    Success(())
  }

  def forceAddFolder(folder: FolderDto): Try[Int] =
    Try {
      db.transaction { implicit session =>
        folderDao.insert(folder)
      }
    }

  def addFolder(folder: FolderDto): Try[Int] =
    validateFolder(folder).flatMap(_ => forceAddFolder(folder))

  def findFolder(folderId: Int): Option[FolderDto] =
    db.transaction { implicit session =>
      folderDao.find(folderId)
    }
}
