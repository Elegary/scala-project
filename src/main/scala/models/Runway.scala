package models

import parser.CsvParser
import scalikejdbc.{SQLSyntaxSupport, WrappedResultSet}

// Model for Runways
case class Runway(
                   id: Int,
                   airportRef: Int,
                   airportIdent: String,
                   lengthFt: Option[Int],
                   widthFt: Option[Int],
                   surface: Option[String],
                   lighted: Boolean,
                   closed: Boolean,
                   leIdent: Option[String],
                   leLatitudeDeg: Option[Double],
                   leLongitudeDeg: Option[Double],
                   leElevationFt: Option[Int],
                   leHeadingDegT: Option[Double],
                   leDisplacedThresholdFt: Option[Int],
                   heIdent: Option[String],
                   heLatitudeDeg: Option[Double],
                   heLongitudeDeg: Option[Double],
                   heElevationFt: Option[Int],
                   heHeadingDegT: Option[Double],
                   heDisplacedThresholdFt: Option[Int]
                 )

object Runway extends SQLSyntaxSupport[Runway] {

  def apply(rs: WrappedResultSet): Runway = new Runway(
    id = rs.int("id"),
    airportRef = rs.int("airport_ref"),
    airportIdent = rs.string("airport_ident"),
    lengthFt = rs.intOpt("length_ft"),
    widthFt = rs.intOpt("width_ft"),
    surface = rs.stringOpt("surface"),
    lighted = rs.boolean("lighted"),
    closed = rs.boolean("closed"),
    leIdent = rs.stringOpt("le_ident"),
    leLatitudeDeg = rs.doubleOpt("le_latitude_deg"),
    leLongitudeDeg = rs.doubleOpt("le_longitude_deg"),
    leElevationFt = rs.intOpt("le_elevation_ft"),
    leHeadingDegT = rs.doubleOpt("le_heading_degT"),
    leDisplacedThresholdFt = rs.intOpt("le_displaced_threshold_ft"),
    heIdent = rs.stringOpt("he_ident"),
    heLatitudeDeg = rs.doubleOpt("he_latitude_deg"),
    heLongitudeDeg = rs.doubleOpt("he_longitude_deg"),
    heElevationFt = rs.intOpt("he_elevation_ft"),
    heHeadingDegT = rs.doubleOpt("he_heading_degT"),
    heDisplacedThresholdFt = rs.intOpt("he_displaced_threshold_ft")
  )

  def from(csvLine: String): Runway = {
    val fields = CsvParser.splitCsvLine(csvLine)

    Runway(
      id = fields(0).toInt,
      airportRef = fields(1).toInt,
      airportIdent = fields(2),
      lengthFt = toOptionalInt(fields(3)),
      widthFt = toOptionalInt(fields(4)),
      surface = toOptionalString(fields(5)),
      lighted = fields(6).toInt == 1,
      closed = fields(7).toInt == 1,
      leIdent = toOptionalString(fields(8)),
      leLatitudeDeg = toOptionalDouble(fields(9)),
      leLongitudeDeg = toOptionalDouble(fields(10)),
      leElevationFt = toOptionalInt(fields(11)),
      leHeadingDegT = toOptionalDouble(fields(12)),
      leDisplacedThresholdFt = toOptionalInt(fields(13)),
      heIdent = toOptionalString(fields(14)),
      heLatitudeDeg = toOptionalDouble(fields(15)),
      heLongitudeDeg = toOptionalDouble(fields(16)),
      heElevationFt = toOptionalInt(fields(17)),
      heHeadingDegT = toOptionalDouble(fields(18)),
      heDisplacedThresholdFt = toOptionalInt(fields(19))
    )
  }

  private def toOptionalInt(value: String): Option[Int] =
    if (value.isEmpty) None else Some(value.toInt)

  private def toOptionalDouble(value: String): Option[Double] =
    if (value.isEmpty) None else Some(value.toDouble)

  private def toOptionalString(value: String): Option[String] =
    if (value.isEmpty) None else Some(value)
}
