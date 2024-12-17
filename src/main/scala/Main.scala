import models.Airport

object Main {
  def main(args: Array[String]): Unit = {
    println("BDML Project")
    val path = os.pwd / "data"
    val airports_lines = os.read.lines(path / "airports.csv")
    airports_lines.drop(1).foreach(airport_line => {
      val airport = Airport.from(airport_line)
      println(airport)
    })

  }
}