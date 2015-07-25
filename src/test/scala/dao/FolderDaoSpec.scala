package dao

import dto.FolderDto
import lib.{Database, DatabaseSupport}
import org.scalatest.{BeforeAndAfter, FunSpec}

class FolderDaoSpec extends FunSpec with BeforeAndAfter {
  val sut: FolderDao = new FolderDaoImpl
  var db: Database = null

  before {
    db = DatabaseSupport.createDatabase()
  }

  after {
    DatabaseSupport.destroyDatabase(db)
  }

  describe("FolderDaoは") {
    it("insertしてfindできる") {
      // Setup
      val dto = FolderDto(0, "aaa", "bbb")

      // Exercise
      db.transaction { implicit session =>
        sut.insert(dto)
      }

      // Verify
      db.transaction { implicit session =>
        val actual = sut.find(1).get
        assert(actual.owner == dto.owner)
        assert(actual.name == dto.name)
      }
    }

    it("insertしてdeleteできる") {
      // Setup
      db.transaction { implicit session =>
        sut.insert(FolderDto(0, "ccc", "ddd"))
      }

      // Exercise
      db.transaction { implicit session =>
        sut.delete(1)
      }

      // Verify
      db.transaction { implicit session =>
        assert(sut.find(1).isEmpty)
      }
    }
  }
}

