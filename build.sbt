/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import Dependencies._

organization := "com.chrism"
name := "play-sample"

version := "0.0.1"

scalaVersion := "2.12.10"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("https://github.com/chrismin1202/play-sample"))
scmInfo := Some(
  ScmInfo(url("https://github.com/chrismin1202/play-sample"), "git@github.com:chrismin1202/play-sample.git")
)
developers := List(
  Developer("chrismin1202", "Donatello", "chrism.1202@gmail.com", url("https://github.com/chrismin1202")),
)

parallelExecution in Test := false

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

resolvers += "chrism commons4s GitHub Package Registry" at "https://maven.pkg.github.com/chrismin1202/commons4s"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayLayoutPlugin)

libraryDependencies ++= Seq(
  Commons4s,
  guice,
  SwaggerPlay2,
  WebjarsPlay,
  SwaggerUi,
  Scalacheck % Test,
  Scalatest % Test,
  Specs2Core % Test,
  Commons4s % Test classifier "tests",
  ScalatestPlusPlay % Test,
)
