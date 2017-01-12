name := "quiz"

version := "1.0"

lazy val `quiz` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

val postgres = "9.4-1204-jdbc4"

val slf4jVersion = "1.7.21"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % postgres,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "joda-time" % "joda-time" % "2.9.6",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  cache ,
  ws   ,
  "org.slf4j" % "slf4j-nop" % slf4jVersion,
  specs2 % Test )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  