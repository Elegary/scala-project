package repository

import models.Runway
import scalikejdbc._

class RunwayRepository {

  def createTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
      CREATE TABLE IF NOT EXISTS runways (
        id SERIAL PRIMARY KEY,
        airport_ref INT NOT NULL,
        airport_ident VARCHAR(10) NOT NULL,
        length_ft INT,
        width_ft INT,
        surface VARCHAR(60),
        lighted BOOLEAN,
        closed BOOLEAN,
        le_ident VARCHAR(10),
        le_latitude_deg DOUBLE,
        le_longitude_deg DOUBLE,
        le_elevation_ft INT,
        le_heading_degT DOUBLE,
        le_displaced_threshold_ft INT,
        he_ident VARCHAR(10),
        he_latitude_deg DOUBLE,
        he_longitude_deg DOUBLE,
        he_elevation_ft INT,
        he_heading_degT DOUBLE,
        he_displaced_threshold_ft INT
      )
      """.execute.apply()
    }
  }

  def insert(runway: Runway): Unit = {
    DB localTx { implicit session =>
      sql"""
      INSERT INTO runways (
        id, airport_ref, airport_ident, length_ft, width_ft, surface, lighted, closed,
        le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_degT, le_displaced_threshold_ft,
        he_ident, he_latitude_deg, he_longitude_deg, he_elevation_ft, he_heading_degT, he_displaced_threshold_ft
      )
      VALUES (
        ${runway.id}, ${runway.airportRef}, ${runway.airportIdent}, ${runway.lengthFt.orNull},
        ${runway.widthFt.orNull}, ${runway.surface.orNull}, ${runway.lighted}, ${runway.closed},
        ${runway.leIdent.orNull}, ${runway.leLatitudeDeg.orNull}, ${runway.leLongitudeDeg.orNull},
        ${runway.leElevationFt.orNull}, ${runway.leHeadingDegT.orNull}, ${runway.leDisplacedThresholdFt.orNull},
        ${runway.heIdent.orNull}, ${runway.heLatitudeDeg.orNull}, ${runway.heLongitudeDeg.orNull},
        ${runway.heElevationFt.orNull}, ${runway.heHeadingDegT.orNull}, ${runway.heDisplacedThresholdFt.orNull}
      )
      """.update.apply()
    }
  }

  def findAll(): List[Runway] = {
    DB readOnly { implicit session =>
      sql"SELECT * FROM runways".map(Runway.apply).list.apply()
    }
  }

  def getTypeOfRunwaysPerCountry(): List[(String, String)] = {
    DB readOnly { implicit session =>
      sql"""
      SELECT DISTINCT c.name, r.surface
      FROM countries c
      JOIN airports a ON c.code = a.iso_country
      JOIN runways r ON a.id = r.airport_ref
      WHERE r.surface IS NOT NULL
      """.map(rs => (rs.string("name"), rs.string("surface"))).list.apply()
    }
  }

  // • The top 10 most common runway latitude (indicated in "le_ident" column)
  def getTop10RunwayLatitudes(): List[(String, Int)] = {
    DB readOnly { implicit session =>
      sql"""
      SELECT le_ident, COUNT(le_ident) AS count
      FROM runways
      WHERE le_ident IS NOT NULL
      GROUP BY le_ident
      ORDER BY count DESC
      LIMIT 10
      """.map(rs => (rs.string("le_ident"), rs.int("count"))).list.apply()
    }
  }

  def getAirportsAndRunwaysByCountryCode(code: String): List[(String, Runway)] = {
    DB readOnly { implicit session =>
      sql"""
      SELECT *
      FROM runways r
      JOIN airports a ON r.airport_ref = a.id
      WHERE a.iso_country = $code
      """.map(rs => (rs.string("name"), Runway(rs))).list.apply()
    }
  }

  def getAirportsAndRunwaysByPartialCountryName(name: String): List[(String, Runway)] = {

    val pattern = s"%${name.toUpperCase}%"

    DB readOnly { implicit session =>
      sql"""
      SELECT *
      FROM runways r
      JOIN airports a ON r.airport_ref = a.id
      JOIN countries c ON a.iso_country = c.code
      WHERE UPPER(c.name) LIKE $pattern
      ORDER BY c.name
      LIMIT 1
      """.map(rs => (rs.string("name"), Runway(rs))).list.apply()
    }
  }

}
