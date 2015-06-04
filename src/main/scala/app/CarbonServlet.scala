package app

import model._
import dto.{Folder => FolderDto}
import services.FolderService
import logic.folder.{FolderComponent => FolderLogicComponent}
import logic.folder.{FolderImpl => FolderLogicImpl}

import java.io.File
import org.scalatra._
import scala.slick.driver.H2Driver.simple._

class CarbonServlet(db: Database) extends ScalatraServlet {

  val userHomeDirectory = scala.util.Properties.envOrElse("HOME", "~")

  val folderService = new FolderService(s"${userHomeDirectory}/.carbon")
    with FolderLogicComponent {
      val folderLogic = new FolderLogicImpl(db); 
  }

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

  // フォルダの新規作成
  get("/new") {
    html.createFolder.render()
  }

  // フォルダのトップ
  get("/folders/:folderId") {
    val folderId = params("folderId").toInt
    folderService.findFolder(folderId) match {
      case Some(folderDto) => html.folder.render(folderDto)
      case None => halt(404)
    }
  }

  // ドキュメントの新規作成
  get("/folders/:folderId/new") {
    val folderId = params("folderId").toInt
    folderService.findFolder(folderId) match {
      case Some(folderDto) => html.newDocument.render(folderDto)
      case None => halt(404)
    }
  }

  post("/new") {
    val folderOwner = params.get("folder_owner").getOrElse("")
    val folderName = params.get("folder_name").getOrElse("")

    if (folderOwner == "" || folderOwner == "") {
      redirect("/error")
    } else {
      val folderId = folderService.addFolder(FolderDto(0, folderOwner, folderName))

      redirect("/folders/" + folderId)
    }
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
        redirect("/")
      case _ =>
        halt(400)
    }
  }
}
