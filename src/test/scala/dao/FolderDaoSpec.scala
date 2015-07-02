package dao

import dto.FolderDto
import lib.SessionSupport
import org.scalatest.FunSpec

class FolderDaoSpec extends FunSpec with SessionSupport {
  val sut: FolderDao= new FolderDaoImpl

  describe("FolderDaoは") {
    it("insertしてfindできる") {
      withSession { implicit session =>
        // Setup
        val dto = FolderDto(0, "aaa", "bbb")

        // Exercise
        sut.insert(dto)

        // Verify
        val actual = sut.find(1).get
        assert(actual.owner == dto.owner)
        assert(actual.name == dto.name)
      }
    }

    it("insertしてdeleteできる") {
      withSession { implicit session =>
        // Setup
        sut.insert(FolderDto(0, "ccc", "ddd"))

        // Exercise
        sut.delete(1)

        // Verify
        assert(sut.find(1).isEmpty)
      }
    }
  }
}

