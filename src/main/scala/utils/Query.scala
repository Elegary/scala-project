package utils

import models.Runway
import repository.RunwayRepository

case class AirportRunways(airportName: String, runways: Seq[Runway])

object Query {
  // get list of airports and runways by country code
  // structure is List with airport_name: String, Runways: List[Runway]
  def queryAirportsAndRunwaysByCountryCode(code: String): Seq[AirportRunways] = {
    val runways = new RunwayRepository().getAirportsAndRunwaysByCountryCode(code)
    runways.groupBy(_._1).map(airport => {
      AirportRunways(airport._1, airport._2.map(_._2).toList)
    }).toSeq
  }

  // get list of airports and runways by partial country name
  // structure is List with airport_name: String, Runways: List[Runway]
  def queryAirportsAndRunwaysByPartialCountryName(name: String): Seq[AirportRunways] = {
    val runways = new RunwayRepository().getAirportsAndRunwaysByPartialCountryName(name)
    runways.groupBy(_._1).map(airport => {
      AirportRunways(airport._1, airport._2.map(_._2).toList)
    }).toSeq
  }

}
