import db.DatabaseSetup
import api.ApiService
import ui.ConsoleApp


object Main {
  def main(args: Array[String]): Unit = {
    println("BDML Project")

    DatabaseSetup.init()

    ApiService.start()

    ConsoleApp.userInterface()

  }

}