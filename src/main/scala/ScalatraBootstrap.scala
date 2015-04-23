import app._
import model._
import javax.servlet.ServletContext
import org.scalatra._
import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.JdbcBackend.Database
import scala.slick.jdbc.meta._

class ScalatraBootstrap extends LifeCycle {
  private def initializeSchema(db: Database) {
    // テーブルが存在しない場合はテーブルを作る
    db.withTransaction { implicit session =>
      if (MTable.getTables.list.size == 0) {
        TableQuery[Users].ddl.create
        TableQuery[Folders].ddl.create
      }
    }
  }

  override def init(context: ServletContext) {
    Class.forName("org.h2.Driver")
    val db = Database.forURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4")

    // とりあえず起動時にスキーマを初期化する
    initializeSchema(db)

    context.mount(new CarbonServlet(db), "/*")
  }
}
