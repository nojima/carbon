package logic.folder

import dto.{Folder => FolderDto}
import model.{Folders => FolderTable, Folder => FolderModel}
import scala.slick.driver.H2Driver.simple._

class FolderImpl(db: Database) extends Folder {
   override def insert(folder: FolderDto) = {
     val folderQuery = TableQuery[FolderTable]
     db.withTransaction { implicit session =>
       folderQuery.insert(FolderModel(0, folder.owner, folder.name))
       1 //TODO: 後でauto incrementにする。
     }
   }

   override def delete(folderId: Int) =
     db.withTransaction{ implicit session =>
       TableQuery[FolderTable].filter(_.id === folderId).delete
     }

   override def find(folderId: Int): Option[FolderDto] =
     db.withTransaction { implicit session =>
        TableQuery[FolderTable]
         .filter(_.id === folderId)
         .firstOption
         .flatMap(x => Some(new FolderDto(x.owner, x.name, x.id)))
     }
}
