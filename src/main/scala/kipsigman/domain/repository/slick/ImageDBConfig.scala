package kipsigman.domain.repository.slick

import kipsigman.domain.entity.ContentImage
import kipsigman.domain.entity.Image

trait ImageDBConfig extends TableGroupConfig {
  import driver.api._
  
  class ImageTable(tag: Tag) extends IdTable[Image](tag, "image") {
    def mimeType = column[String]("mime_type")
    def width = column[Int]("width")
    def height = column[Int]("height")
    def caption = column[Option[String]]("caption")
    
    def * = (id.?, mimeType, width, height, caption) <> ((Image.apply _).tupled, Image.unapply)
  }
  
  val imageTableQuery = TableQuery[ImageTable]
  
  
  case class ContentImageRow(
    contentClass: String,
    contentId: Int,
    imageId: Int,
    displayType: ContentImage.DisplayType,
    displayPosition: Int) {
    
    def this(contentClass: String, entity: ContentImage) = this(contentClass, entity.contentId, entity.image.id.get, entity.displayType, entity.displayPosition)
    
    def toEntity(image: Image): ContentImage = {
      ContentImage(contentId, image, displayType, displayPosition) 
    }
  }
    
  class ContentImageTable(tag: Tag) extends Table[ContentImageRow](tag, "content_image") {
    implicit val displayTypeMapper = MappedColumnType.base[ContentImage.DisplayType, String](
      {displayType => displayType.name},
      {str => ContentImage.DisplayType.apply(str)}
    )
  
    def contentClass = column[String]("content_class")
    def contentId = column[Int]("content_id")
    def imageId = column[Int]("image_id")
    def displayType = column[ContentImage.DisplayType]("display_type")
    def displayPosition = column[Int]("display_position")
    
    def * = (contentClass, contentId, imageId, displayType, displayPosition) <> (ContentImageRow.tupled, ContentImageRow.unapply)
  }
  
  val contentImageTableQuery = TableQuery[ContentImageTable]
  
  implicit class ContentImageExtensions(q: driver.api.Query[ContentImageTable, ContentImageRow, Seq]) {
    // specify mapping of relationship to address
    def withImage = q.join(imageTableQuery).on(_.imageId === _.id)
  }
}