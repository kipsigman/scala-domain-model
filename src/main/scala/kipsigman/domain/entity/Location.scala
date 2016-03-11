package kipsigman.domain.entity

import kipsigman.domain.service.UrlService

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