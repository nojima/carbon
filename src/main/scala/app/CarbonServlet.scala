package app

import dto.{UserDto, FolderDto}
import services.{UserService, FolderService}
import dao.{UserDaoImpl, UserDaoComponent, FolderDaoComponent, FolderDaoImpl}

import org.scalatra._

class CarbonServlet() extends ScalatraServlet {
  val folderService = new FolderService with FolderDaoComponent {
    val folderDao = new FolderDaoImpl()
  }
  val userService = new UserService with UserDaoComponent {
    val userDao = new UserDaoImpl()
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
    html.users.render(userService.listUsers())
  }

  post("/users") {
    val name = params.get("name").getOrElse("")
    val password = params.get("password").getOrElse("")
    userService.addUser(UserDto(0, name, password))
    redirect("/")
  }
}
