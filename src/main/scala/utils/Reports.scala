package utils

import repository.{AirportRepository, RunwayRepository}


case class CountryRunway(country: String, runways: Seq[String])

object Reports {

  // Transform ist[(String, Int)] to List of String, Int with the country and count sorted in descending order
  def get10CountriesWithMostAirports(): Seq[(String, Int)] = {
    AirportRepository.getAirportsCountByCountry(10, ascending = false)
  }

  def get10CountriesWithLeastAirports(): Seq[(String, Int)] = {
    AirportRepository.getAirportsCountByCountry(10, ascending = true)
  }

  // get a list of country_name: String, runway_surface: list of String
  def getTypeOfRunwaysPerCountry(): Seq[CountryRunway] = {
    val runways = RunwayRepository.getTypeOfRunwaysPerCountry()
    val groupedByCountry = runways.groupBy(_._1)
    // use case class to return the result
    groupedByCountry.map(country => {
      CountryRunway(country._1, country._2.map(_._2))
      }).toSeq
    }

  def getTop10RunwayLatitudes(): Seq[(String, Int)] = {
    RunwayRepository.getTop10RunwayLatitudes()
  }

  }

