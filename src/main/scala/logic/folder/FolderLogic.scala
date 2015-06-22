package logic.folder

import dto.FolderDto

trait FolderLogic {
  def insert(folder: FolderDto): Int

  def delete(folderId: Int)

  def find(folderId: Int): Option[FolderDto]
}

trait FolderLogicComponent {
  val folderLogic: FolderLogic
}
