package kipsigman.domain.entity

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
}