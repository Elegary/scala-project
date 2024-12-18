package models

import parser.CsvParser
import scalikejdbc.{SQLSyntaxSupport, WrappedResultSet}

// model
case class Airport(
                    id: Int,
                    ident: String,
                    airportType: String,
                    name: String,
                    latitude: Double,
                    longitude: Double,
                    elevationFt: Option[Int],
                    continent: String,
                    isoCountry: String,
                    isoRegion: String,
                    municipality: Option[String],
                    scheduledService: String,
                    gpsCode: Option[String],
                    iataCode: Option[String],
                    localCode: Option[String],
                    homeLink: Option[String],
                    wikipediaLink: Option[String],
                    keywords: Option[String]
                  )

object Airport extends SQLSyntaxSupport[Airport] {
  override val tableName = "airports"

  // Define column names mapping
  def apply(rs: WrappedResultSet): Airport = new Airport(
    id = rs.int("id"),
    ident = rs.string("ident"),
    airportType = rs.string("airport_type"),
    name = rs.string("name"),
    latitude = rs.double("latitude"),
    longitude = rs.double("longitude"),
    elevationFt = rs.intOpt("elevation_ft"),
    continent = rs.string("continent"),
    isoCountry = rs.string("iso_country"),
    isoRegion = rs.string("iso_region"),
    municipality = rs.stringOpt("municipality"),
    scheduledService = rs.string("scheduled_service"),
    gpsCode = rs.stringOpt("gps_code"),
    iataCode = rs.stringOpt("iata_code"),
    localCode = rs.stringOpt("local_code"),
    homeLink = rs.stringOpt("home_link"),
    wikipediaLink = rs.stringOpt("wikipedia_link"),
    keywords = rs.stringOpt("keywords")
  )

  def from(csvLine: String): Airport = {

    val fields = CsvParser.splitCsvLine(csvLine)

    Airport(
      id = fields(0).toInt,
      ident = fields(1),
      airportType = fields(2),
      name = fields(3),
      latitude = fields(4).toDouble,
      longitude = fields(5).toDouble,
      elevationFt = if (fields(6).isEmpty) None else Some(fields(6).toInt),
      continent = fields(7),
      isoCountry = fields(8),
      isoRegion = fields(9),
      municipality = if (fields(10).isEmpty) None else Some(fields(10)),
      scheduledService = fields(11),
      gpsCode = if (fields(12).isEmpty) None else Some(fields(12)),
      iataCode = if (fields(13).isEmpty) None else Some(fields(13)),
      localCode = if (fields(14).isEmpty) None else Some(fields(14)),
      homeLink = if (fields(15).isEmpty) None else Some(fields(15)),
      wikipediaLink = if (fields(16).isEmpty) None else Some(fields(16)),
      keywords = if (fields(17).isEmpty) None else Some(fields(17))
    )
  }
}