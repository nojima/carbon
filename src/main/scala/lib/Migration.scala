package lib

import dao.FolderDaoImpl
import dto.FolderDto
import scalikejdbc._

// Database Migration を行うオブジェクトになる予定。
// 今のところスキーマの新規作成のみ実装。
object Migration {
  def create(implicit session: DBSession) {
    // テーブルが存在しない場合はテーブルを作る
    sql"""CREATE TABLE IF NOT EXISTS "folders" (
      "id" INTEGER PRIMARY KEY AUTO_INCREMENT,
      "owner" VARCHAR(255) NOT NULL,
      "name" VARCHAR(255) NOT NULL
    )""".execute().apply()

    sql"""CREATE TABLE IF NOT EXISTS "users" (
      "id" INTEGER PRIMARY KEY AUTO_INCREMENT,
      "name" VARCHAR(255) NOT NULL,
      "password" VARCHAR(255) NOT NULL
    )""".execute().apply()
  }

  def insertInitialData(implicit session: DBSession) {
    // 初期データをつっこむ
    val folderDao = new FolderDaoImpl()
    folderDao.insert(new FolderDto(0, "a", "a"))
    folderDao.insert(new FolderDto(0, "a", "b"))
  }

  def drop(implicit session: DBSession): Unit = {
    sql"""DROP TABLE "folders", "users" """.execute().apply()
  }
}
