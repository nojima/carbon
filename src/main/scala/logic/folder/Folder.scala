package logic.folder

import dto.{Folder => FolderDto}

trait Folder {
  def withInsertingFolder[S](folder: FolderDto)(f: Int => S): S

  def find(folderId: Int): Option[FolderDto]
}

trait FolderComponent {
  val folderLogic: Folder
}
