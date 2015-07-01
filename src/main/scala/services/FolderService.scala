package services

import dto.FolderDto
import dao.FolderDaoComponent
import scalikejdbc._

class FolderService { this: FolderDaoComponent =>
  def addFolder(folder: FolderDto): Int = {
    if (folder.owner.isEmpty || folder.name.isEmpty) {
      throw new IllegalArgumentException("Could not create folder")
    }
    DB localTx { implicit session =>
      folderDao.insert(folder)
    }
  }

  def findFolder(folderId: Int): Option[FolderDto] =
    DB.readOnly { implicit session =>
      folderDao.find(folderId)
    }
}
