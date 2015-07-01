import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import play.twirl.sbt.SbtTwirl

object CarbonBuild extends Build {
  val Organization = "com.example"
  val Name = "Carbon"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.5"
  val ScalatraVersion = "2.3.0"

  lazy val project = Project (
    "carbon",
    file("."),
    settings = ScalatraPlugin.scalatraWithJRebel ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.json4s"   %% "json4s-jackson" % "3.2.9",
        "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
        "org.mockito" % "mockito-core" % "1.10.19",
        "ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "container",
        "org.pegdown" % "pegdown" % "1.5.0",
        "javax.servlet" % "javax.servlet-api" % "3.1.0",
        "com.typesafe.slick" %% "slick" % "2.1.0",
        "com.h2database" % "h2" % "1.3.166"
      )
    )
  ).enablePlugins(SbtTwirl)
}
