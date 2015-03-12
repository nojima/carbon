package app

import org.scalatra._

import dto.Folder

import services.FolderService

class CarbonServlet extends ScalatraServlet {

  val userHomeDirectory = scala.util.Properties.envOrElse("HOME", "~")

  val folderService = new FolderService(s"${userHomeDirectory}/.carbon");

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
    html.createFolder.render()
  }

  post("/new") {
    val folderOwner = params.get("folder_owner").getOrElse("");
    val folderName = params.get("folder_name").getOrElse("");

    if (folderOwner == "" || folderOwner == "") {
      redirect("/error");
    } else {
      folderService.ensureFolderExists(dto.Folder(folderOwner, folderName));

      redirect("/" + folderOwner + "/" + folderName);
    }
  }
}
