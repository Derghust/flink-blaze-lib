plugins {
    id("gradle.library-conventions")

    id("gradle.scala-common-conventions")
    id("gradle.flink-common-conventions")
}

group = "com.github.derghust.flinkblaze.impl"

dependencies {
    api(project(":operator"))
}
