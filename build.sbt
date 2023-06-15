ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / libraryDependencies += "org.apache.flink" % "flink-streaming-java" % "1.17.1" % "provided"
ThisBuild / libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"

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
