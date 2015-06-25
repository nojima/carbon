package app

import model._
import dto.FolderDto
import services.FolderService
import dao.FolderDaoComponent
import dao.FolderDaoImpl
import util.DatabaseUtil

import org.scalatra._
import slick.driver.H2Driver.api._

class CarbonServlet(db: Database) extends ScalatraServlet {
  val folderService = new FolderService with FolderDaoComponent {
    val folderDao = new FolderDaoImpl(db)
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
    val folderId = folderService.addFolder(FolderDto(0, folderOwner, folderName))
    redirect("/folders/" + folderId)
  }

  get("/signup") {
    html.signup.render
  }

  get("/users") {
    // TODO: とりあえずベタ書き。後で直す。
    val users = TableQuery[Users]
    val action = users.sortBy(_.id).to[Seq].result
    val result = DatabaseUtil.runAction(db, action)
    html.users.render(result)
  }

  post("/users") {
    // TODO: 仮実装。後で書き直す。
    (params.get("name"), params.get("password")) match {
      case (Some(name), Some(password)) if name.length > 0 && password.length > 0 =>
        val users = TableQuery[Users]
        val action = users += User(0, name, password)
        DatabaseUtil.runAction(db, action)
        redirect("/")
      case _ =>
        halt(400)
    }
  }
}
