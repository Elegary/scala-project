package models

import parser.CsvParser
import scalikejdbc.SQLSyntaxSupport

case class Country(
                    id: Int,
                    code: String,
                    name: String,
                    continent: String,
                    wikipediaLink: String,
                    keywords: Option[String]
                  )

object Country extends SQLSyntaxSupport[Country] {

  def apply(rs: scalikejdbc.WrappedResultSet): Country = new Country(
    id = rs.int("id"),
    code = rs.string("code"),
    name = rs.string("name"),
    continent = rs.string("continent"),
    wikipediaLink = rs.string("wikipedia_link"),
    keywords = rs.stringOpt("keywords")
  )

  def from(csvLine: String): Country = {
    val fields = CsvParser.splitCsvLine(csvLine)

    Country(
      id = fields(0).toInt,
      code = fields(1),
      name = fields(2),
      continent = fields(3),
      wikipediaLink = fields(4),
      keywords = Option(fields(5)).filter(_.nonEmpty) // Handle optional keywords
    )
  }
}