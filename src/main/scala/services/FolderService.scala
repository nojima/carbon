package services

import dto.{Folder => FolderDto}
import logic.folder.{FolderComponent => FolderLogicComponent}

class FolderService { this: FolderLogicComponent =>
  def addFolder(folder: FolderDto): Int = {
    if (folder.owner.isEmpty || folder.name.isEmpty) {
      throw new IllegalArgumentException("Could not create folder");
    }
    folderLogic.insert(folder)
  }

  def findFolder(folderId: Int): Option[FolderDto] = folderLogic.find(folderId)
}
