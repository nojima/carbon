package app

import org.scalatra._

import services.FolderService

class CarbonServlet extends ScalatraServlet {

  val folderService = new FolderService("~/.carbon");

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
    html.createFolder.render
  }

  post("/new") {
    val folderOwner       = params.get("folder_owner").getOrElse("");
    val folderName        = params.get("folder_name").getOrElse("");
    val folderDescription = params.get("folder_description").getOrElse("");
    if (folderOwner == "" || folderOwner == "" || folderDescription == "") {
      redirect("/error");
    } else {
      folderService.ensureFolderExists(
        folderOwner, folderName, folderDescription);
      redirect("/" + folderOwner + "/" + folderName);
    }
  }
}
