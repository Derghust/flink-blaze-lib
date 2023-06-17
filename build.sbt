ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val flinkVersion = "1.17.1"

ThisBuild / libraryDependencies += "org.apache.flink" % "flink-streaming-java" % flinkVersion % "provided"
ThisBuild / libraryDependencies += "org.apache.flink" % "flink-clients" % flinkVersion % Test
ThisBuild / libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.16" % Test
ThisBuild / libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.7" % Test
ThisBuild / libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.7" % Test

lazy val scala_2_12 = "2.12.18"
lazy val scala_2_13 = "2.13.11"
lazy val scala_3_2  = "3.2.2"

lazy val utils      = Project("utils", file("utils")).cross
lazy val utils_2_12 = utils(scala_2_12)
lazy val utils_2_13 = utils(scala_2_13)
lazy val utils_3_2  = utils(scala_3_2)

lazy val operator = Project("operator", file("operator")).cross
lazy val operator_2_12 = operator(scala_2_12)
  .dependsOn(utils_2_12)
  .settings(
    libraryDependencies += "com.github.blemale" %% "scaffeine" % "5.2.1"
  )
lazy val operator_2_13 = operator(scala_2_13)
  .dependsOn(utils_2_13)
  .settings(
    libraryDependencies += "com.github.blemale" %% "scaffeine" % "5.2.1"
  )
lazy val operator_3_2 = operator(scala_3_2)
  .dependsOn(utils_3_2)
  .settings(
    libraryDependencies += "com.github.blemale" %% "scaffeine" % "5.2.1"
  )

lazy val impl      = Project("impl", file("impl")).cross
lazy val impl_2_12 = impl(scala_2_12).dependsOn(operator_2_12)
lazy val impl_2_13 = impl(scala_2_13).dependsOn(operator_2_13)
lazy val impl_3_2  = impl(scala_3_2).dependsOn(operator_3_2)
