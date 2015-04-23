package logic.folder

import dto.{Folder => FolderDto}
import model.{Folders => FoldersTable, Folder => FolderModel}
import scala.slick.driver.H2Driver.simple._

class FolderImpl(db: Database) extends Folder {
   override def withInsertingFolder[S](folder: FolderDto)(f: => S): S = {
     val folderQuery = TableQuery[FoldersTable]
     db.withTransaction { implicit session =>
       folderQuery.insert(FolderModel(0, folder.owner, folder.name))
       f
     }
   }
}
