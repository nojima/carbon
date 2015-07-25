import app._
import dto.FolderDto
import dao.FolderDaoImpl
import javax.servlet.ServletContext
import lib.{Migration, DatabaseImpl}
import org.scalatra._
import scalikejdbc._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val db = new DatabaseImpl('carbon, 4)
    db.transaction { implicit session =>
      Migration.create
      Migration.insertInitialData
    }

    context.mount(new CarbonServlet(db), "/*")
    context.mount(new InternalApiServlet(), "/api/*")
  }
}
