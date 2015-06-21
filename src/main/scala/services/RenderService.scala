package services

import org.pegdown.Extensions
import org.pegdown.PegDownProcessor

class RenderService {
  private val processor = new PegDownProcessor(org.pegdown.Extensions.ALL)

  def renderMarkdown(text: String): String = processor.markdownToHtml(text)
}
