import db.DatabaseSetup
import models.{Airport, Country, Runway}
import os.Path
import repository.{AirportRepository, CountryRepository, DBSetup, RunwayRepository}
import scalikejdbc._
import scalikejdbc.config._

// DBs.setup/DBs.setupAll loads specified JDBC driver classes.


object Main {
  def main(args: Array[String]): Unit = {
    println("BDML Project")

    val data_path = os.pwd / "data"

    DatabaseSetup.init()

    println("Loading airports...")
    load_airports(data_path / "airports.csv")
    println("Loading countries...")
    load_countries(data_path / "countries.csv")
    println("Loading runways...")
    load_runways(data_path / "runways.csv")

    println("Done!")

    while (true) {
      println("Enter a country code")
      val country_code = scala.io.StdIn.readLine()
      val allCountries = new CountryRepository().findAll()
      val country = allCountries.find(_.code == country_code)
      println(country)
      val runways = new RunwayRepository().getAiportsAndRunwaysByCountryCode(country_code)
      runways.groupBy(_._1).map(airport => {
        println(s"Airport: ${airport._1}")
        airport._2.foreach(runway => println(s"\t- ${runway._2.niceDisplay}"))
      })
      val tenHighestAirports = new AirportRepository().getAirportsCountByCountry(10, ascending = false)
      tenHighestAirports.foreach(println)
      val tenLowestAirports = new AirportRepository().getAirportsCountByCountry(10, ascending = true)
      tenLowestAirports.foreach(println)
      val typesOfRunways = new RunwayRepository().getTypeOfRunwaysPerCountry()
      // result is a list of tuples (country_name, runway_surface), we group by country name
      val groupedByCountry = typesOfRunways.groupBy(_._1)
      // we print the results
      groupedByCountry.map(country => {
        println(s"Country: ${country._1}")
        country._2.foreach(runway => println(s"\t- ${runway._2}"))
      })

      val runwaysLatitudes = new RunwayRepository().getTop10RunwayLatitudes()
      runwaysLatitudes.foreach(println)
    }

  }

  def load_airports(path: Path): Unit = {
    val airportRepo = new AirportRepository()
    airportRepo.createTable()
    val airports_lines = os.read.lines.stream(path)
    airports_lines.drop(1).foreach(airport_line => {
      val airport = Airport.from(airport_line)
      airportRepo.insert(airport)
    })
  }

  def load_countries(path: Path): Unit = {
    val countriesRepo = new CountryRepository()
    countriesRepo.createTable()
    val countries_lines = os.read.lines(path)
    countries_lines.drop(1).foreach(country_line => {
      val country = Country.from(country_line)
      countriesRepo.insert(country)
    })
  }

  def load_runways(path: Path): Unit = {
    val runwaysRepo = new RunwayRepository()
    runwaysRepo.createTable()
    val runways_lines = os.read.lines(path)
    runways_lines.drop(1).foreach(runway_line => {
      val runway = Runway.from(runway_line)
      runwaysRepo.insert(runway)
    })
  }

}