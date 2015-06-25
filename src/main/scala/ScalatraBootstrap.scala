import app._
import model._
import dto.FolderDto
import dao.FolderDaoImpl
import util.DatabaseUtil

import javax.servlet.ServletContext
import org.scalatra._
import slick.driver.H2Driver.api._
import slick.jdbc.meta.MTable
import scala.concurrent.ExecutionContext.Implicits.global

class ScalatraBootstrap extends LifeCycle {
  private def initializeSchema(db: Database) {
    // テーブルが存在しない場合はテーブルを作る
    val tableExists = MTable.getTables.map(_.size == 0)
    if (DatabaseUtil.runAction(db, tableExists)) {
      val createSchema = (TableQuery[Users].schema ++ TableQuery[Folders].schema).create
      DatabaseUtil.runAction(db, createSchema)
    }

    // 初期データをつっこむ
    val folderDao = new FolderDaoImpl(db)
    folderDao.insert(new FolderDto(-1, "a", "a"))
    folderDao.insert(new FolderDto(-1, "a", "b"))
  }

  override def init(context: ServletContext) {
    Class.forName("org.h2.Driver")
    val db = Database.forURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4")

    // とりあえず起動時にスキーマを初期化する
    initializeSchema(db)

    context.mount(new CarbonServlet(db), "/*")
    context.mount(new InternalApiServlet(), "/api/*")

  }
}
