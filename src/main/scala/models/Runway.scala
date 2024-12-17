package models

import parser.CsvParser

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

object Runway {
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
