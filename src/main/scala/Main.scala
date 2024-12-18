import models.{Airport, Country, Runway}
import os.Path
import repository.{AirportRepository, DBSetup}
import scalikejdbc._
import scalikejdbc.config._

// DBs.setup/DBs.setupAll loads specified JDBC driver classes.


object Main {
  def main(args: Array[String]): Unit = {
    println("BDML Project")

    val data_path = os.pwd / "data"

    DBSetup.initialize()

    load_airports(data_path / "airports.csv")
    load_countries(data_path / "countries.csv")

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
    val countries_lines = os.read.lines(path)
    countries_lines.drop(1).foreach(country_line => {
      val country = Country.from(country_line)
      println(country)
    })
  }
  
}