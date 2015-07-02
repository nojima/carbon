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
      sut.addFolder(dto)

      // Verify
      verify(folderDaoMock).insert(equal(dto))(any[DBSession])
    }

    it("ownerが空の場合に例外を投げる") {
      intercept[IllegalArgumentException] {
        sut.addFolder(FolderDto(0, "", "name"))
      }
    }

    it("nameが空の場合に例外を投げる") {
      intercept[IllegalArgumentException] {
        sut.addFolder(FolderDto(0, "owner", ""))
      }
    }
  }

  describe("findFolderで") {
    it("folderを取ってこれる") {
      // Exercise
      sut.findFolder(1)

      // Verify
      verify(folderDaoMock).find(equal(1))(any[DBSession])
    }
  }
}
