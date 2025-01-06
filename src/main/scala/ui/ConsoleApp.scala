package ui

import repository.CountryRepository
import utils.Query.{queryAirportsAndRunwaysByCountryCode, queryAirportsAndRunwaysByPartialCountryName}
import utils.Reports.{get10CountriesWithLeastAirports, get10CountriesWithMostAirports, getTop10RunwayLatitudes, getTypeOfRunwaysPerCountry}

object ConsoleApp {

    def userInterface(): Unit = {
        // User interface for the console application
        println("----------------------------------------")
        println("Welcome to our Scala project for BDML !")
        println("CAUSSIGNAC Romain, BELLPEAU Raphael")
        println("----------------------------------------")
        println()
        while(true) {
            println("Please select an option:")
            // Query or Report
            println("1. Query")
            println("2. Report")
            println("3. Exit")

            val option = scala.io.StdIn.readLine()

            option match {
                case "1" => queryInterface()
                case "2" => reportInterface()
                case "3" => System.exit(0)
                case _ => println("Invalid option")
            }

        }
    }

    def queryInterface(): Unit = {
        // User interface for the query option
        println("Please select an option:")
        // Query by country code
        println("1. Query by country code")
        println("2. Query by partial/fuzzy country name")
        println("3. Back")

        val option = scala.io.StdIn.readLine()

        option match {
            case "1" => displayCountryAirportsAndRunwaysByCode()
            case "2" => displayCountryAirportsAndRunwaysByPartialName()
            case "3" => userInterface()
            case _ => println("Invalid option")
        }
    }

    def reportInterface(): Unit = {
        // User interface for the report option
        println("Please select an option:")
        // Reports
        println("1. 10 countries with most airports")
        println("2. 10 countries with least airports")
        println("3. Types of runways per country")
        println("4. Top 10 runway latitudes")
        println("5. Back")

        val option = scala.io.StdIn.readLine()

        option match {
            case "1" => display10CountriesWithMostAirports()
            case "2" => display10CountriesWithLeastAirports()
            case "3" => displayTypesOfRunwaysPerCountry()
            case "4" => display10RunwayLatitudes()
            case "5" => userInterface()
            case _ => println("Invalid option")
        }
    }

    def display10CountriesWithMostAirports(): Unit = {
        // Display the 10 countries with the most airports
        println("10 countries with the most airports:")
        val tenHighestAirports = get10CountriesWithMostAirports()
        // display position and country, get country name from findCountryByCode
        tenHighestAirports.zipWithIndex.foreach { case ((countryCode, count), index) =>
            val country = CountryRepository.findCountryByCode(countryCode)
            val countryName = country match {
                case Some(c) => c.name
                case None => "Unknown"
            }
            println(s"${index + 1}. $countryName - $count airports")
        }
    }

    def display10CountriesWithLeastAirports(): Unit = {
        // Display the 10 countries with the least airports
        println("10 countries with the least airports:")
        val tenLowestAirports = get10CountriesWithLeastAirports()
        // display position and country, get country name from findCountryByCode
        tenLowestAirports.zipWithIndex.foreach { case ((countryCode, count), index) =>
            val country = CountryRepository.findCountryByCode(countryCode)
            val countryName = country match {
                case Some(c) => c.name
                case None => "Unknown"
            }
            println(s"${index + 1}. $countryName - $count airport(s)")
        }
    }

    def displayTypesOfRunwaysPerCountry(): Unit = {
        // Display the types of runways per country
        println("Types of runways per country:")
        val countryRunways = getTypeOfRunwaysPerCountry()
        // display country and runway types
        countryRunways.foreach(cr => {
            println(s"Country: ${cr.country}")
            cr.runways.map(runway => println(s"\t- $runway"))
        })
    }

    def display10RunwayLatitudes(): Unit = {
        // Display the top 10 runway latitudes
        println("Top 10 runway latitudes:")
        val runwaysLatitudes = getTop10RunwayLatitudes()
        // display runway and count
        runwaysLatitudes.zipWithIndex.foreach { case ((runway, count), index) =>
            println(s"${index + 1}. $runway - $count runways")
        }
    }

    def displayCountryAirportsAndRunwaysByCode(): Unit = {
        // Display the airports and runways for a country code
        println("Please enter a country code:")
        val countryCode = scala.io.StdIn.readLine()
        val airportsRunways = queryAirportsAndRunwaysByCountryCode(countryCode)
        // display airport and runways
        // check if no results
        if (airportsRunways.isEmpty) {
            println("No results found")
        } else {
            // show country name
            val country = CountryRepository.findCountryByCode(countryCode)
            val countryName = country match {
                case Some(c) => c.name
                case None => "Unknown"
            }
            println(s"Country: $countryName")
            airportsRunways.foreach(ar => {
                println(s"Airport: ${ar.airportName}")
                ar.runways.map(runway => println(s"\t- ${runway.niceDisplay}"))
            })
        }
    }

    def displayCountryAirportsAndRunwaysByPartialName(): Unit = {
        // Prompt the user for a country name (partial or full)
        println("Please enter a partial or full country name:")
        val countryName = scala.io.StdIn.readLine()

        // Query for airports and runways
        val countryAirportsRunwaysOpt = queryAirportsAndRunwaysByPartialCountryName(countryName)

        // Check if results were found
        countryAirportsRunwaysOpt match {
            case Some((country, airportsRunways)) =>
                // Display the country name
                println(s"Country: $country")
                // Loop through each airport and its runways
                airportsRunways.foreach { ar =>
                    println(s"Airport: ${ar.airportName}")
                    ar.runways.foreach { runway =>
                        println(s"\t- ${runway.niceDisplay}")
                    }
                }
            case None =>
                // No country found
                println("No results found")
        }
    }


}
