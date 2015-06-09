package logic.folder

import dto.{Folder => FolderDto}

trait Folder {
  def insert(folder: FolderDto): Int

  def delete(folderId: Int)

  def find(folderId: Int): Option[FolderDto]
}

trait FolderComponent {
  val folderLogic: Folder
}
