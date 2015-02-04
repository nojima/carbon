package app

import model._

import org.scalatra._
import scala.slick.driver.H2Driver.simple._

class CarbonServlet(db: Database) extends ScalatraServlet {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="/hello-twirl">hello to Twirl</a>.
      </body>
    </html>
  }

  get("/hello-twirl") {
    html.helloTwirl.render(new java.util.Date)
  }

  get("/new") {
      html.createRepository.render
  }

  get("/signup") {
    html.signup.render
  }

  get("/users") {
    // TODO: とりあえずベタ書き。後で直す。
    val userQuery = TableQuery[Users]
    val users = db.withTransaction { implicit session =>
      userQuery.sortBy(_.id).run
    }
    html.users.render(users)
  }

  post("/users") {
    // TODO: 仮実装。後で書き直す。
    (params.get("name"), params.get("password")) match {
      case (Some(name), Some(password)) if name.length > 0 && password.length > 0 =>
        val userQuery = TableQuery[Users]
        db.withTransaction { implicit session =>
          userQuery.insert(User(0, name, password))
        }
        redirect("/users")
      case _ =>
        halt(400)
    }
  }
}
