import app._
import model._

import org.scalatra._
import javax.servlet.ServletContext
import scala.slick.jdbc.JdbcBackend.Database
import scala.slick.driver.H2Driver.simple._

class ScalatraBootstrap extends LifeCycle {
  private def initializeSchema(db: Database) {
    val userQuery = TableQuery[Users]
    db.withTransaction { implicit session =>
      userQuery.ddl.create
    }
  }

  override def init(context: ServletContext) {
    val db = Database.forURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4")

    // とりあえず起動時にスキーマを初期化する
    initializeSchema(db)

    context.mount(new CarbonServlet(db), "/*")
  }
}
