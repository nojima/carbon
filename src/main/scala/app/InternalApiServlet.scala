package app

import org.scalatra._
import org.scalatra.json._
import org.json4s.{DefaultFormats, Formats}

import services.RenderService

case class MarkdownRenderForm(text: String)
case class MarkdownRenderOutputForm(success: Boolean, result: String)

class InternalApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    contentType = formats("json")
  }

  val renderService = new RenderService()

  post("/markdown/render.json") {
    val text = parsedBody.extract[MarkdownRenderForm].text
    new MarkdownRenderOutputForm(true, renderService.renderMarkdown(text))
  }
}
