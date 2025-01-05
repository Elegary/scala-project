package db

import scalikejdbc._
import scalikejdbc.config._


object DatabaseSetup {
  def init(): Unit = {
    // Load the JDBC driver and set up the connection pool
    Class.forName("org.h2.Driver")
    DBs.setupAll()
    // Set global settings if needed
  }
}