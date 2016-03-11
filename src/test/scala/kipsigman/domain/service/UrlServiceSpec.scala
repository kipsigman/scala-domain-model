package kipsigman.domain.service

import org.scalatest.Matchers
import org.scalatest.WordSpec

class UrlServiceSpec extends WordSpec with Matchers {

  "isValidUrl" should {
    "match valid URL" in {
      UrlService.isValidUrl("http://www.thesith.com/vader.html") shouldBe true
      UrlService.isValidUrl("www.thesith.com/vader.html") shouldBe false
      UrlService.isValidUrl("xxx http://www.thesith.com/vader.html") shouldBe false
    }
  }

  "urlDecode" should {
    "decode string in URL" in {
      UrlService.urlDecode("some+crap+to+be+in+a+querystring%3F%26") shouldBe "some crap to be in a querystring?&"
      UrlService.urlDecode("http%3A%2F%2Fwww.thesith.com%2Fvader.html") shouldBe "http://www.thesith.com/vader.html"
    }
  }

  "urlEncode" should {
    "encode string for URL" in {
      UrlService.urlEncode("some crap to be in a querystring?&") shouldBe "some+crap+to+be+in+a+querystring%3F%26"
      UrlService.urlEncode("http://www.thesith.com/vader.html") shouldBe "http%3A%2F%2Fwww.thesith.com%2Fvader.html"
      UrlService.urlDecode(UrlService.urlEncode("http://www.thesith.com/vader.html")) shouldBe "http://www.thesith.com/vader.html"
    }
  }

}