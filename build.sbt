ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaBDML1S7"
  )

libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.9.1"