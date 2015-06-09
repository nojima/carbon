package services

import java.io.File

import dto.{Folder => FolderDto}
import logic.folder.{FolderComponent => FolderLogicComponent}
import scala.slick.driver.H2Driver.simple._

class FolderService(baseDir: String) { this: FolderLogicComponent => 
  def makeFolderPath(folder: FolderDto): String =
    s"${baseDir}/${folder.owner}/${folder.name}"

  def addFolder(folder: FolderDto): Int = {
    val folderId = folderLogic.insert(folder)

    val folderPath = makeFolderPath(folder)
    val folderDirectory = new File(folderPath)
    if (!folderDirectory.mkdirs()) {
      folderLogic.delete(folderId);
      throw new RuntimeException("Failed to create directory: " + folderPath)
    }

    folderId
  }

  def findFolder(folderId: Int): Option[FolderDto] = folderLogic.find(folderId)
}
