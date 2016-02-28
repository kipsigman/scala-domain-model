package kipsigman.domain.repository

import scala.concurrent.Future

import kipsigman.domain.entity.Content.ContentClass
import kipsigman.domain.entity.ContentImage
import kipsigman.domain.entity.Image

trait ImageRepository {
  def deleteImage(id: Int): Future[Int]
  
  def findImage(id: Int): Future[Option[Image]]
  
  def saveImage(entity: Image): Future[Image]
  
  def deleteContentImage(contentClass: ContentClass, contentId: Int, imageId: Int): Future[Int]
  
  def findContentImage(contentClass: ContentClass, contentId: Int, imageId: Int): Future[Option[ContentImage]]
  
  def findContentImages(contentClass: ContentClass, contentId: Int): Future[Seq[ContentImage]]
  
  def saveContentImage(contentClass: ContentClass, entity: ContentImage): Future[ContentImage]
}