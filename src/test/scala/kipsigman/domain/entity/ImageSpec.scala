package kipsigman.domain.entity

import org.scalatest.Matchers
import org.scalatest.WordSpec

class ImageSpec extends WordSpec with Matchers {
  /////////// Sample data
  
  val image = Image(Option(88), "image/jpeg", 400, 200, Option("Razor Sentinel"))
  
  val baseUrl = "http://www.razorsentinel.com/images"
  val fileName = "logo.jpg"
  val cssId = Option("site-logo")
  val cssClass = Option("nav-logo")
  val s3Bucket = "razorsentinel"
  val s3Path = Option("public/images")
  
  val expectedUrl = "http://www.razorsentinel.com/images/logo.jpg"
  val expectedS3BaseUrl = "https://s3.amazonaws.com/razorsentinel/public/images"
  val expectedS3Url = s"$expectedS3BaseUrl/$fileName"
  val expectedS3NoPathBaseUrl = s"https://s3.amazonaws.com/razorsentinel"
  val expectedS3NoPathUrl = s"$expectedS3NoPathBaseUrl/$fileName"
  /////////// end Sample data
  
  "fileExtension" should {
    "pick correct file extension for mime type" in {
      image.fileExtension shouldBe "jpg"
      image.copy(mimeType = "image/gif").fileExtension shouldBe "gif"
      image.copy(mimeType = "image/png").fileExtension shouldBe "png"
      image.copy(mimeType = "image/bmp").fileExtension shouldBe "bmp"
      
    }
    "throw exception for invalid type" in {
      an [IllegalArgumentException] should be thrownBy image.copy(mimeType = "image/bogus").fileExtension
    }
  }
  
  "filename" should {
    "return None if id is undefined" in {
      image.copy(id = None).filename shouldBe None
    }
    "concat id and fileExtension" in {
      image.filename shouldBe Some("88.jpg")
      image.copy(mimeType = "image/gif").filename shouldBe Some("88.gif")
      image.copy(mimeType = "image/png").filename shouldBe Some("88.png")
      image.copy(mimeType = "image/bmp").filename shouldBe Some("88.bmp")
      
    }
    "throw exception for invalid type" in {
      an [IllegalArgumentException] should be thrownBy image.copy(mimeType = "image/bogus").filename
    }
  }
  
}