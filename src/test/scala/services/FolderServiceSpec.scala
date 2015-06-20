package services

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.mockito.Mockito._
import org.mockito.Matchers._

import dto.{Folder => FolderDto}

class FolderServiceSpec extends FunSpec with BeforeAndAfter {
  var sut: FolderService = _
  var folderLogicMock: logic.folder.Folder = _

  before {
    folderLogicMock = mock(classOf[logic.folder.Folder])
    sut = new FolderService with logic.folder.FolderComponent
    {
      val folderLogic = folderLogicMock
    }
  }

  describe("addFolderは") {
    it("insertできる") {
      // SetUp
      val dto = new FolderDto(0, "owner", "name")

      // Exercise
      sut.addFolder(dto)

      // Verify
      verify(folderLogicMock).insert(dto)
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
      verify(folderLogicMock).find(1)
    }
  }
}
