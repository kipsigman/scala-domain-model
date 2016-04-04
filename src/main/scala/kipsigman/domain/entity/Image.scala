package kipsigman.domain.entity

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.MimeTypes

/**
 * Metadata for an image
 */
case class Image(
  id: Option[Int] = None,
  mimeType: String,
  width: Int,
  height: Int,
  caption: Option[String] = None) extends IdEntity {
  
  val fileExtension: String = Image.fileExtension(mimeType)
  
  val filename: Option[String] = id.map(definedId => Image.filename(definedId, fileExtension))
  
  val thumbnailFilename: Option[String] = id.map(definedId => Image.thumbnailFilename(definedId, fileExtension))
}

object Image {
  def fileExtension(mimeType: String): String = mimeType match {
    case "image/gif" => "gif"
    case "image/jpeg" => "jpg"
    case "image/png" => "png"
    case _ => MimeTypes.defaultTypes.find(kv => kv._2 == mimeType).map(_._1).getOrElse(throw new IllegalArgumentException(s"Invalid Mime Type $mimeType"))
  }
  
  def filename(id: Int, fileExtension: String): String = s"${id}.${fileExtension}"
  
  def thumbnailFilename(id: Int, fileExtension: String): String = s"${id}-thumbnail.${fileExtension}"
  
  implicit val reads: Reads[Image] = (
    (JsPath \ "id").readNullable[Int] and
    (JsPath \ "mimeType").read[String] and
    (JsPath \ "width").read[Int] and
    (JsPath \ "height").read[Int] and
    (JsPath \ "caption").readNullable[String]
  )(Image.apply _)
  
  implicit val writes: Writes[Image] = (
    (JsPath \ "id").writeNullable[Int] and
    (JsPath \ "mimeType").write[String] and
    (JsPath \ "width").write[Int] and
    (JsPath \ "height").write[Int] and
    (JsPath \ "caption").writeNullable[String]
  )(unlift(Image.unapply))
}