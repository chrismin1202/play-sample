object Dependencies {

  import sbt._

  // Make sure to use the versions that are compatible with commons4s
  private val PlayVersion: String = "2.7.3"
  private val ScalacheckVersion: String = "1.14.0"
  private val ScalatestVersion: String = "3.0.8"
  private val Specs2CoreVersion: String = "4.7.0"
  private val ScalatestPlusPlayVersion: String = "4.0.3"

  val Commons4s: ModuleID = "com.chrism" %% "commons4s" % "0.0.5"

  val PlayLogback: ModuleID = "com.typesafe.play" %% "play-logback" % PlayVersion

  val SwaggerPlay2: ModuleID = "io.swagger" %% "swagger-play2" % "1.7.1"
  val WebjarsPlay: ModuleID = "org.webjars" %% "webjars-play" % PlayVersion
  val SwaggerUi: ModuleID = "org.webjars" % "swagger-ui" % "3.23.8"

  val Scalacheck: ModuleID = "org.scalacheck" %% "scalacheck" % ScalacheckVersion
  val Scalatest: ModuleID = "org.scalatest" %% "scalatest" % ScalatestVersion
  val Specs2Core: ModuleID = "org.specs2" %% "specs2-core" % Specs2CoreVersion
  val ScalatestPlusPlay: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % ScalatestPlusPlayVersion
}