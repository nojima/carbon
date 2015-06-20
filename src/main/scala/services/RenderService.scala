package services

import org.pegdown.PegDownProcessor

class RenderService() {
  private val processor = new PegDownProcessor()

  def renderMarkdown(text: String): String = processor.markdownToHtml(text)
}
