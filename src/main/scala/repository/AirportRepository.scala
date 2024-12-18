package repository

import scalikejdbc._
import models.Airport

class AirportRepository {

  def createTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
      CREATE TABLE IF NOT EXISTS airports (
        id INT PRIMARY KEY,
        ident VARCHAR(255),
        airport_type VARCHAR(255),
        name VARCHAR(255),
        latitude DOUBLE,
        longitude DOUBLE,
        elevation_ft INT,
        continent VARCHAR(10),
        iso_country VARCHAR(10),
        iso_region VARCHAR(10),
        municipality VARCHAR(255),
        scheduled_service VARCHAR(10),
        gps_code VARCHAR(10),
        iata_code VARCHAR(10),
        local_code VARCHAR(10),
        home_link VARCHAR(1000),
        wikipedia_link VARCHAR(1000),
        keywords VARCHAR(1000)
      )
      """.execute.apply()
    }
  }

  def insert(airport: Airport): Unit = {
    DB localTx { implicit session =>
      sql"""
      INSERT INTO airports (id, ident, airport_type, name, latitude, longitude, elevation_ft, continent,
                            iso_country, iso_region, municipality, scheduled_service, gps_code, iata_code,
                            local_code, home_link, wikipedia_link, keywords)
      VALUES (
        ${airport.id}, ${airport.ident}, ${airport.airportType}, ${airport.name},
        ${airport.latitude}, ${airport.longitude}, ${airport.elevationFt.orNull},
        ${airport.continent}, ${airport.isoCountry}, ${airport.isoRegion},
        ${airport.municipality.orNull}, ${airport.scheduledService}, ${airport.gpsCode.orNull},
        ${airport.iataCode.orNull}, ${airport.localCode.orNull}, ${airport.homeLink.orNull},
        ${airport.wikipediaLink.orNull}, ${airport.keywords.orNull}
      )
      """.update.apply()
    }
  }

  def findAll(): List[Airport] = {
    DB readOnly { implicit session =>
      sql"SELECT * FROM airports".map(Airport.apply).list.apply()
    }
  }

  // Define more methods for particular queries as needed
}