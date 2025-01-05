package api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.Airport
import models.Runway
import repository.AirportRepository
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import utils.{AirportRunways, CountryRunway, Reports}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val airportFormat: RootJsonFormat[Airport] = jsonFormat18(Airport.apply)
  implicit val countryRunwayFormat: RootJsonFormat[CountryRunway] = jsonFormat2(CountryRunway)
  implicit val runwayFormat: RootJsonFormat[Runway] = jsonFormat20(Runway.apply)
  implicit val airportRunwaysFormat: RootJsonFormat[AirportRunways] = jsonFormat2(AirportRunways)
}


// define the routes
object ApiService extends JsonSupport {

  implicit val system = ActorSystem("AirportSystem")


  val route: Route = path("airports") {
    get {
      complete(AirportRepository.findAll())
    }
  } ~ path("airports" / "runways" / Segment) { code =>
    get {
      complete(utils.Query.queryAiportsAndRunwaysByCountryCode(code))
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
  } ~ path("runways" / "latitudes") {
    get {
      complete(Reports.getTop10RunwayLatitudes())
    }
  }

  def start(): Unit = {
    val server = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/")
  }
}
