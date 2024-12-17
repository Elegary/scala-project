package models

import parser.CsvParser

case class Country(
                    id: Int,
                    code: String,
                    name: String,
                    continent: String,
                    wikipediaLink: String,
                    keywords: Option[String]
                  )

object Country {
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