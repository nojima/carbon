package logic.folder

import dto.{Folder => FolderDto}

trait Folder {
  def withInsertingFolder[S](folder: FolderDto)(f: => S): S
}

trait FolderComponent {
  val folderLogic: Folder
}
