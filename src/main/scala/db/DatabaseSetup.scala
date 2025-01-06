package db

import models.{Airport, Country, Runway}
import os.Path
import repository.{AirportRepository, CountryRepository, RunwayRepository}
import scalikejdbc._
import scalikejdbc.config._


object DatabaseSetup {
  def init(): Unit = {
    // Load the JDBC driver and set up the connection pool
    Class.forName("org.h2.Driver")
    DBs.setupAll()
    // Set global settings if needed

    val data_path = os.pwd / "data"

    println("Loading airports...")
    load_airports(data_path / "airports.csv")
    println("Loading countries...")
    load_countries(data_path / "countries.csv")
    println("Loading runways...")
    load_runways(data_path / "runways.csv")

    println("Done!")

  }

  def load_airports(path: Path): Unit = {
    AirportRepository.createTable()
    val airports_lines = os.read.lines.stream(path)
    airports_lines.drop(1).foreach(airport_line => {
      val airport = Airport.from(airport_line)
      AirportRepository.insert(airport)
    })
  }

  def load_countries(path: Path): Unit = {
    CountryRepository.createTable()
    val countries_lines = os.read.lines(path)
    countries_lines.drop(1).foreach(country_line => {
      val country = Country.from(country_line)
      CountryRepository.insert(country)
    })
  }

  def load_runways(path: Path): Unit = {
    RunwayRepository.createTable()
    val runways_lines = os.read.lines(path)
    runways_lines.drop(1).foreach(runway_line => {
      val runway = Runway.from(runway_line)
      RunwayRepository.insert(runway)
    })
  }

}