package services

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.mockito.Mockito._
import org.mockito.Matchers._

import dto.FolderDto
import dao.FolderDao
import dao.FolderDaoComponent

class FolderServiceSpec extends FunSpec with BeforeAndAfter {
  var sut: FolderService = _
  var folderDaoMock: FolderDao = _

  before {
    folderDaoMock = mock(classOf[FolderDao])
    sut = new FolderService with FolderDaoComponent
    {
      val folderDao = folderDaoMock
    }
  }

  describe("addFolderは") {
    it("insertできる") {
      // SetUp
      val dto = new FolderDto(0, "owner", "name")

      // Exercise
      sut.addFolder(dto)

      // Verify
      verify(folderDaoMock).insert(dto)
    }

    it("ownerが空の場合に例外を投げる") {
      intercept[IllegalArgumentException] {
        sut.addFolder(new FolderDto(0, "", "name"))
      }
    }

    it("nameが空の場合に例外を投げる") {
      intercept[IllegalArgumentException] {
        sut.addFolder(new FolderDto(0, "owner", ""))
      }
    }
  }

  describe("findFolderで") {
    it("folderを取ってこれる") {
      // Exercise
      sut.findFolder(1)

      // Verify
      verify(folderDaoMock).find(1)
    }
  }
}
