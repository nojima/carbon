package services

import dto.FolderDto
import dao.FolderDaoComponent
import lib.Database

class FolderService(db: Database) { this: FolderDaoComponent =>
  def addFolder(folder: FolderDto): Int = {
    if (folder.owner.isEmpty || folder.name.isEmpty) {
      throw new IllegalArgumentException("Could not create folder")
    }
    db.transaction { implicit session =>
      folderDao.insert(folder)
    }
  }

  def findFolder(folderId: Int): Option[FolderDto] =
    db.transaction { implicit session =>
      folderDao.find(folderId)
    }
}
