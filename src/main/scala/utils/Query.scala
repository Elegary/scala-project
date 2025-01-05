package utils

import models.Runway
import repository.RunwayRepository

case class AirportRunways(airportName: String, runways: Seq[Runway])

object Query {
  // get list of airports and runways by country code
  // structure is List with airport_name: String, Runways: List[Runway]
  def queryAiportsAndRunwaysByCountryCode(code: String): Seq[AirportRunways] = {
    val runways = new RunwayRepository().getAiportsAndRunwaysByCountryCode(code)
    runways.groupBy(_._1).map(airport => {
      AirportRunways(airport._1, airport._2.map(_._2).toList)
    }).toSeq
  }

}
