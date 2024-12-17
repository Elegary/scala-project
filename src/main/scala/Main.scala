import models.{Airport, Country, Runway}
import repository.DBSetup
import scalikejdbc._
import scalikejdbc.config._

// DBs.setup/DBs.setupAll loads specified JDBC driver classes.

object Main {
  def main(args: Array[String]): Unit = {
    println("BDML Project")

    DBSetup.initialize()

  }

  def load_files(): Unit = {
    val path = os.pwd / "data"
    val airports_lines = os.read.lines(path / "airports.csv")
    airports_lines.drop(1).foreach(airport_line => {
      val airport = Airport.from(airport_line)
      println(airport)
    })

    val countries_lines = os.read.lines(path / "countries.csv")
    countries_lines.drop(1).foreach(country_line => {
      val country = Country.from(country_line)
      println(country)
    })

    val runways_lines = os.read.lines(path / "runways.csv")
    runways_lines.drop(1).foreach(runway_line => {
      val runway = Runway.from(runway_line)
      println(runway)
    })
  }
}