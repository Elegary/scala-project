package parser

import scala.annotation.tailrec

object CsvParser {
  def splitCsvLine(line: String): Array[String] = {
    @tailrec
    def parse(remaining: List[Char],
              currentField: List[Char],
              fields: List[String],
              insideQuotes: Boolean): List[String] = {

      remaining match {
        case Nil =>
          (currentField.reverse.mkString :: fields).reverse

        case '"' :: tail if !insideQuotes =>
          parse(tail, currentField, fields, true)

        case '"' :: '"' :: tail if insideQuotes =>
          parse(tail, '"' :: currentField, fields, true)

        case '"' :: tail if insideQuotes =>
          parse(tail, currentField, fields, false)

        case ',' :: tail if !insideQuotes =>
          parse(tail, Nil, currentField.reverse.mkString :: fields, false)

        case head :: tail =>
          parse(tail, head :: currentField, fields, insideQuotes)
      }
    }

    parse(line.toList, Nil, Nil, false).toArray
  }
}