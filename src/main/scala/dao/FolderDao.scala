package dao

import dto.FolderDto

trait FolderDao {
  def insert(folder: FolderDto): Int

  def delete(folderId: Int)

  def find(folderId: Int): Option[FolderDto]
}

trait FolderDaoComponent {
  val folderDao: FolderDao
}
