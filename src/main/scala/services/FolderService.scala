package services

import java.io.File

import util.FileLockUtil

class FolderService(baseDir: String) {

  def makeFolderPath(folder: dto.Folder): String =
    s"${baseDir}/${folder.owner}/${folder.name}"

  def ensureFolderExists(folder: dto.Folder): Unit = {
    val folderPath = makeFolderPath(folder)
    val folderDirectory = new File(folderPath)

    FileLockUtil.lockWith(folderDirectory) {
      if (folderDirectory.exists()) {
        return
      }

      if (!folderDirectory.mkdirs()) {
        throw new RuntimeException(
          "Failed to create directory: " + folderPath)
      }
    }
  }
}
