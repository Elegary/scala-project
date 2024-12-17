package repository

import scalikejdbc._
import scalikejdbc.config._

object DBSetup {

  // Initialize the database, creating tables if they do not exist.
  def initialize(): Unit = {
    // Define your database schema creation SQL

    DBs.setupAll()

    implicit val session = AutoSession

    val createAirportTable = sql"""
      CREATE TABLE IF NOT EXISTS airport (
        id INT PRIMARY KEY,
        ident VARCHAR(255) NOT NULL,
        airportType VARCHAR(255) NOT NULL,
        name VARCHAR(255) NOT NULL,
        latitude DOUBLE NOT NULL,
        longitude DOUBLE NOT NULL,
        elevation_ft INT,
        continent VARCHAR(255) NOT NULL,
        iso_country VARCHAR(255) NOT NULL,
        iso_region VARCHAR(255) NOT NULL,
        municipality VARCHAR(255),
        scheduled_service VARCHAR(255) NOT NULL,
        gps_code VARCHAR(255),
        iata_code VARCHAR(255),
        local_code VARCHAR(255),
        home_link VARCHAR(255),
        wikipedia_link VARCHAR(255),
        keywords TEXT
      )
    """.execute.apply()
  }
}