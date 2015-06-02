package logic.folder

import dto.{Folder => FolderDto}
import model.{Folders => FoldersTable, Folder => FolderModel}
import scala.slick.driver.H2Driver.simple._

class FolderImpl(db: Database) extends Folder {
   override def withInsertingFolder[S](folder: FolderDto)(f: Int => S): S = {
     val folderQuery = TableQuery[FoldersTable]
     db.withTransaction { implicit session =>
       folderQuery.insert(FolderModel(0, folder.owner, folder.name))
       f(0)
     }
   }

   override def find(folderId: Int): Option[FolderDto] = {
     db.withTransaction { implicit session =>
       TableQuery[FoldersTable]
         .filter({_.id === folderId})
         .firstOption
         .flatMap({ x => Some(new FolderDto(x.owner, x.name, x.id))})
     }
   }
}
