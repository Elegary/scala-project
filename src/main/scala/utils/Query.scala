package utils

import models.Runway
import repository.RunwayRepository

case class AirportRunways(airportName: String, runways: Seq[Runway])

object Query {
  // get list of airports and runways by country code
  // structure is List with airport_name: String, Runways: List[Runway]
  def queryAirportsAndRunwaysByCountryCode(code: String): Seq[AirportRunways] = {
    val runways = RunwayRepository.getAirportsAndRunwaysByCountryCode(code)
    runways.groupBy(_._1).map(airport => {
      AirportRunways(airport._1, airport._2.map(_._2).toList)
    }).toSeq
  }

  // get list of airports and runways by partial country name
  // structure is List with airport_name: String, Runways: List[Runway]
  def queryAirportsAndRunwaysByPartialCountryName(partialName: String): Option[(String, Seq[AirportRunways])] = {

    // Retrieve data using the repository method
    val runwaysData: List[(String, String, Runway)] = RunwayRepository.getAirportsAndRunwaysByPartialCountryName(partialName)

    // If the list is not empty, proceed to process the data
    runwaysData.headOption.map { firstEntry =>
      // Extract the country name from the first entry
      val countryName = firstEntry._1

      // Group the data by airport name
      val airportsWithRunways = runwaysData.groupBy(_._2).map { case (airportName, entries) =>
        // Create a list of runways for each airport
        AirportRunways(airportName, entries.map(_._3))
      }.toSeq

      // Return the country name and the list of AirportRunways
      (countryName, airportsWithRunways)
    }
  }

}
