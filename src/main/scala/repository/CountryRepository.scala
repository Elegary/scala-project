package repository

import models.Country

import scalikejdbc._


class CountryRepository {

  def createTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
      CREATE TABLE IF NOT EXISTS countries (
        id INT PRIMARY KEY,
        code VARCHAR(255),
        name VARCHAR(255),
        continent VARCHAR(255),
        wikipedia_link VARCHAR(1000),
        keywords VARCHAR(1000)
      )
      """.execute.apply()
    }
  }

  def insert(country: Country): Unit = {
    DB localTx { implicit session =>
      sql"""
      INSERT INTO countries (
        id, code, name, continent, wikipedia_link, keywords
      )
      VALUES (
        ${country.id}, ${country.code}, ${country.name}, ${country.continent},
        ${country.wikipediaLink}, ${country.keywords.orNull}
      )
      """.update.apply()
    }
  }

  def findAll(): List[Country] = {
    DB readOnly { implicit session =>
      sql"SELECT * FROM countries".map(Country.apply).list.apply()
    }
  }

}
