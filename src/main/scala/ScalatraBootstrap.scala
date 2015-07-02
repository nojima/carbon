import app._
import dto.FolderDto
import dao.FolderDaoImpl
import javax.servlet.ServletContext
import lib.DatabaseImpl
import org.scalatra._
import scalikejdbc._

class ScalatraBootstrap extends LifeCycle {
  private def initializeSchema() {
    // テーブルが存在しない場合はテーブルを作る
    // TODO: より適切な場所に置く
    DB localTx { implicit session =>
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

    // 初期データをつっこむ
    DB localTx { implicit session =>
      val folderDao = new FolderDaoImpl()
      folderDao.insert(new FolderDto(0, "a", "a"))
      folderDao.insert(new FolderDto(0, "a", "b"))
    }
  }

  override def init(context: ServletContext) {
    ConnectionPool.singleton("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4;MODE=PostgreSQL", "", "")

    // とりあえず起動時にスキーマを初期化する
    initializeSchema()

    val db = new DatabaseImpl
    context.mount(new CarbonServlet(db), "/*")
    context.mount(new InternalApiServlet(), "/api/*")
  }
}
