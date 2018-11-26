name := """aml"""

lazy val root = (project in file(".")).enablePlugins(PlayScala)

version := "1.0"

scalaVersion := "2.12.6"

routesImport += "utils.FilterSort._"

unmanagedJars in Compile := ((baseDirectory.value / "./libs") ** "*.jar").classpath

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  "com.microsoft.sqlserver" % "mssql-jdbc" % "6.2.1.jre8",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "net.codingwell" %% "scala-guice" % "4.1.1",
  "com.iheart" %% "ficus" % "1.4.2",
  "com.mohiva" %% "play-silhouette" % "5.0.1",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.1",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.1",
  "com.mohiva" %% "play-silhouette-persistence" % "5.0.1",
  "com.mohiva" %% "play-silhouette-testkit" % "5.0.1" % "test",
  "com.unboundid" % "unboundid-ldapsdk" % "2.3.6",
  "javax.mail" % "mail" % "1.4.4"
)
libraryDependencies += guice
libraryDependencies += specs2 % Test
libraryDependencies += filters
libraryDependencies += cacheApi
