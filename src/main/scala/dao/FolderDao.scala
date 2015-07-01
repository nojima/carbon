package dao

import dto.FolderDto
import scalikejdbc.DBSession

trait FolderDao {
  def insert(folder: FolderDto)(implicit session: DBSession): Int

  def delete(folderId: Int)(implicit session: DBSession): Int

  def find(folderId: Int)(implicit session: DBSession): Option[FolderDto]
}

trait FolderDaoComponent {
  val folderDao: FolderDao
}
