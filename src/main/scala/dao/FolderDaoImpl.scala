package dao

import dto.FolderDto
import scalikejdbc._

class FolderDaoImpl extends FolderDao {
  override def insert(folder: FolderDto)(implicit session: DBSession): Int =
    sql"""INSERT INTO "folders" ("owner", "name") VALUES (${folder.owner}, ${folder.name})""".update().apply()

  override def delete(folderId: Int)(implicit session: DBSession): Int =
    sql"""DELETE FROM "folders" WHERE "id" = $folderId""".update().apply()

  override def find(folderId: Int)(implicit session: DBSession): Option[FolderDto] =
    sql"""SELECT "id", "owner", "name" FROM "folders" WHERE "id" = $folderId"""
      .map(r => FolderDto(r.int("id"), r.string("owner"), r.string("name")))
      .single().apply()
}
