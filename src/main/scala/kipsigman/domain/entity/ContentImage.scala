package kipsigman.domain.entity

case class ContentImage(
  contentId: Int,
  image: Image,
  displayType: ContentImage.DisplayType,
  displayPosition: Int)

object ContentImage {
  sealed abstract class DisplayType(val name: String) {
    override def toString: String = name
  }
  
  object DisplayType {
    case object Primary extends DisplayType("primary")
    case object Sidebar extends DisplayType("sidebar")
    case object Thumbnail extends DisplayType("heading")
    
    val all: Set[DisplayType] = Set(Primary, Sidebar, Thumbnail)
    
    def apply(name: String): DisplayType = {
      all.find(s => s.name == name) match {
        case Some(item) => item
        case None => throw new IllegalArgumentException(s"Invalid DisplayType: $name")
      }
    }  
  }
  
}