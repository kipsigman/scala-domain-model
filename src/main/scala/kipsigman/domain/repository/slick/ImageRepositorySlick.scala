package kipsigman.domain.repository.slick

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import javax.inject.Inject
import javax.inject.Singleton
import kipsigman.domain.entity.Content.ContentClass
import kipsigman.domain.entity.ContentImage
import kipsigman.domain.entity.Image
import kipsigman.domain.repository.ImageRepository

import play.api.db.slick.DatabaseConfigProvider
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

@Singleton
class ImageRepositorySlick @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends ImageRepository with ImageDBConfig {

  import driver.api._
  
  override protected val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
  
  override def deleteImage(id: Int): Future[Int] = {
    db.run(imageTableQuery.filter(_.id === id).delete)
  }
  
  override def findImage(id: Int): Future[Option[Image]] = {
    db.run(imageTableQuery.filter(_.id === id).result).map(_.headOption)
  }
  
  override def saveImage(entity: Image): Future[Image] = {
    if (entity.isPersisted) {
      db.run(imageTableQuery.insertOrUpdate(entity)).map(_ => entity)
    } else {
      db.run((imageTableQuery returning imageTableQuery.map(_.id)) += entity).map(id =>
        entity.copy(id = Some(id))
      )
    }
  }
  
  private def contentImageQuery(contentClass: ContentClass): Query[ContentImageTable, ContentImageRow, Seq] = {
    contentImageTableQuery.filter(_.contentClass === contentClass.toString)
  }
  
  private def contentImageResults(q: Query[ContentImageTable, ContentImageRow, Seq]): Future[Seq[ContentImage]] = {
    db.run(q.withImage.result).map(joinSeq =>
      joinSeq.map(join => join._1.toEntity(join._2))
    )
  }
  
  override def deleteContentImage(contentClass: ContentClass, contentId: Int, imageId: Int): Future[Int] = {
    db.run(contentImageQuery(contentClass).filter(_.contentId === contentId).filter(_.imageId === imageId).delete).flatMap(rowCount =>
      deleteImage(imageId)
    )
  }
  
  override def findContentImage(contentClass: ContentClass, contentId: Int, imageId: Int): Future[Option[ContentImage]] = {
    val q = contentImageQuery(contentClass).filter(_.contentId === contentId).filter(_.imageId === imageId).sortBy(_.displayPosition)
    contentImageResults(q).map(_.headOption)
  }
  
  override def findContentImages(contentClass: ContentClass, contentId: Int): Future[Seq[ContentImage]] = {
    val q = contentImageQuery(contentClass).filter(_.contentId === contentId).sortBy(_.displayPosition)
    contentImageResults(q)
  }
  
  override def saveContentImage(contentClass: ContentClass, entity: ContentImage): Future[ContentImage] = {
    saveImage(entity.image).flatMap(savedImage => {
      val imageSavedEntity = entity.copy(image = savedImage)
      val row = new ContentImageRow(contentClass.toString, imageSavedEntity)
      db.run(contentImageTableQuery.insertOrUpdate(row)).map(id => imageSavedEntity)  
    })
  }
}