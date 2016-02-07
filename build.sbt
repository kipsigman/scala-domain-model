import Dependencies._

name := """scala-domain-model"""

organization := "kipsigman"

version := "0.1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "org.mockito" % "mockito-core" % "1.10.19" % "test"
)

dependencyOverrides += "com.typesafe.play" %% "play-jdbc-api" % playVersion