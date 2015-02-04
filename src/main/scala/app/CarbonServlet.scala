package app

import org.scalatra._
import services.RepositoryService

class CarbonServlet extends ScalatraServlet {
  
  case class RepositoryCreationForm(owner: String, name: String, description: String)
  
  val repositoryService = new RepositoryService

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

  get("/create") { form =>
    repositoryService.ensureRepositoryExists(form.userName, form.ownder, form.repositoryName)
    redirect("/${form.owner}/${form.name}")    
  }
}
