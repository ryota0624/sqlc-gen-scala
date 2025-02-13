ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "example"
  )
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
  .settings(
    libraryDependencies += "org.postgresql" % "postgresql" % "42.7.5",
    libraryDependencies += "org.testcontainers" % "postgresql" % "1.20.4" % Test,
    libraryDependencies += "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.41.8" % Test,
    libraryDependencies += "com.dimafeng" %% "testcontainers-scala-postgresql" % "0.41.8" % Test,
  )
