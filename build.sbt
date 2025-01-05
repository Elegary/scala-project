ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaBDML1S7"
  )

libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.9.1"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"         % "4.3.2",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "4.3.2",
  "com.h2database"  %  "h2"                  % "2.2.224",
  "ch.qos.logback"  %  "logback-classic"     % "1.5.6",

  // API REST
    "com.typesafe.akka" %% "akka-http" % "10.5.3",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
    "com.typesafe.akka" %% "akka-stream" % "2.8.8"
)
