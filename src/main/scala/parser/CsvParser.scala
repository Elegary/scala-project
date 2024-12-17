package parser

// Define as a singleton
object CsvParser {
  def splitCsvLine(line: String): Array[String] = {
    val result = new scala.collection.mutable.ArrayBuffer[String]
    val current = new StringBuilder
    var inQuotes = false

    for (c <- line) {
      c match {
        case '"' => inQuotes = !inQuotes
        case ',' if !inQuotes =>
          result += current.toString.trim.stripPrefix("\"").stripSuffix("\"")
          current.clear()
        case _ => current.append(c)
      }
    }

    result += current.toString.trim.stripPrefix("\"").stripSuffix("\"")
    result.toArray
  }
}
