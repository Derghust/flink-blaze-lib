plugins {
    id("gradle.library-conventions")

    id("gradle.scala-common-conventions")
    id("gradle.flink-common-conventions")
}

group = "com.github.derghust.flinkblaze.operator"

val scalaVersion = project.extra["scalaVersion"] as String

val scaffeineVersion = "5.2.0"

dependencies {
    implementation("com.github.blemale:scaffeine_${scalaVersion}:${scaffeineVersion}")
}
