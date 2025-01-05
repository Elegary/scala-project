package api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.Airport
import repository.AirportRepository
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import utils.{CountryRunway, Reports}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val airportFormat: RootJsonFormat[Airport] = jsonFormat18(Airport.apply)
  implicit val countryRunwayFormat: RootJsonFormat[CountryRunway] = jsonFormat2(CountryRunway)
}


// define the routes
object ApiService extends JsonSupport {

  implicit val system = ActorSystem("AirportSystem")


  val route: Route = path("airports") {
    get {
      complete(AirportRepository.findAll())
    }
  } ~ path("airports" / "counts" / "highest") {
    get {
      complete(Reports.get10CountriesWithMostAirports())
    }
  } ~ path("airports" / "counts" / "lowest") {
    get {
      complete(Reports.get10CountriesWithLeastAirports())
    }
  } ~ path("runways" / "types") {
    get {
      complete(Reports.getTypeOfRunwaysPerCountry())
    }
  }

  def start(): Unit = {
    val server = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/")
  }
}
