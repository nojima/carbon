package services

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.mockito.Mockito._
import org.mockito.Matchers._

import dto.FolderDto
import logic.folder.FolderLogic
import logic.folder.FolderLogicComponent

class FolderServiceSpec extends FunSpec with BeforeAndAfter {
  var sut: FolderService = _
  var folderLogicMock: FolderLogic = _

  before {
    folderLogicMock = mock(classOf[FolderLogic])
    sut = new FolderService with FolderLogicComponent
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
