package kipsigman.domain.service

import java.net.{ URLDecoder, URLEncoder }

import scala.util.matching.Regex

trait UrlUtils {
  
  // Regex
  val UrlRegex: Regex = """(https?|ftp|file|mailto)://[-A-Za-z0-9+&@#/%?=~_|!:,.;\{\}]*[-A-Za-z0-9+&@#/%=~_|\{\}]""".r
  val UrlMatchRegex: Regex = ("^" + UrlRegex.toString + "$").r

  def isValidUrl(url: String): Boolean = {
    UrlMatchRegex.findFirstMatchIn(url).isDefined
  }
  
  def urlDecode(url: String): String = URLDecoder.decode(url, "UTF-8")

  def urlEncode(url: String): String = URLEncoder.encode(url, "UTF-8")
}

object UrlService extends UrlUtils