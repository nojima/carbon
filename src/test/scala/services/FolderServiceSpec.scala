package services

import lib.FakeDatabaseImpl
import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.mockito.Mockito._
import org.mockito.Matchers.{any, eq => equal}

import dto.FolderDto
import dao.FolderDao
import dao.FolderDaoComponent
import scalikejdbc.DBSession

class FolderServiceSpec extends FunSpec with BeforeAndAfter {
  var sut: FolderService = _
  var folderDaoMock: FolderDao = _

  before {
    folderDaoMock = mock(classOf[FolderDao])
    sut = new FolderService(new FakeDatabaseImpl) with FolderDaoComponent {
      val folderDao = folderDaoMock
    }
  }

  describe("addFolderは") {
    it("insertできる") {
      // SetUp
      val dto = FolderDto(0, "owner", "name")

      // Exercise
      val t = sut.addFolder(dto)

      // Verify
      assert(t.isSuccess)
      verify(folderDaoMock).insert(equal(dto))(any[DBSession])
    }

    it("ownerが空の場合に失敗する") {
      // Exercise
      val t = sut.addFolder(FolderDto(0, "", "name"))

      // Verify
      assert(t.isFailure)
    }

    it("nameが空の場合に失敗する") {
      // Exercise
      val t = sut.addFolder(FolderDto(0, "owner", ""))

      // Verify
      assert(t.isFailure)
    }
  }

  describe("findFolderで") {
    it("folderを取ってこれる") {
      // Exercise
      val t = sut.findFolder(1)

      // Verify
      verify(folderDaoMock).find(equal(1))(any[DBSession])
    }
  }
}
