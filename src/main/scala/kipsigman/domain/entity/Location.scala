package kipsigman.domain.entity

import kipsigman.domain.service.UrlService
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Location(
  city: String,
  state: String,
  postalCode: Option[String],
  country: String,
  latitude: Option[Double],
  longitude: Option[Double]) {
  
  lazy val urlEncoded: String = UrlService.urlEncode(s"$city,$state,$country".toLowerCase)
  
  lazy val displayText: String = s"$city, $state, $country"
}

object Location {
  implicit val reads: Reads[Location] = (
    (JsPath \ "city").read[String] and
    (JsPath \ "state").read[String] and
    (JsPath \ "postalCode").readNullable[String] and
    (JsPath \ "country").read[String] and
    (JsPath \ "latitude").readNullable[Double] and
    (JsPath \ "longitude").readNullable[Double]
  )(Location.apply _)
  
  implicit val writes: Writes[Location] = (
    (JsPath \ "city").write[String] and
    (JsPath \ "state").write[String] and
    (JsPath \ "postalCode").writeNullable[String] and
    (JsPath \ "country").write[String] and
    (JsPath \ "latitude").writeNullable[Double] and
    (JsPath \ "longitude").writeNullable[Double]
  )(unlift(Location.unapply))
}