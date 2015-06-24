package services

import dto.FolderDto
import dao.FolderDaoComponent

class FolderService { this: FolderDaoComponent =>
  def addFolder(folder: FolderDto): Int = {
    if (folder.owner.isEmpty || folder.name.isEmpty) {
      throw new IllegalArgumentException("Could not create folder");
    }
    folderDao.insert(folder)
  }

  def findFolder(folderId: Int): Option[FolderDto] = folderDao.find(folderId)
}
