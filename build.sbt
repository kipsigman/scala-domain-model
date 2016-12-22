import Dependencies._

name := """scala-domain-model"""
organization := "kipsigman"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.mockito" % "mockito-core" % "2.3.11" % "test"
)

dependencyOverrides += "com.typesafe.play" %% "play-jdbc-api" % playVersion

licenses += ("Apache-2.0", url("https://github.com/kipsigman/scala-domain-model/blob/master/LICENSE"))
homepage := Some(url("https://github.com/kipsigman/scala-domain-model"))
scmInfo := Some(ScmInfo(url("https://github.com/kipsigman/scala-domain-model"), "scm:git:git://github.com:kipsigman/scala-domain-model.git"))