plugins {
    id("gradle.library-conventions")

    id("gradle.scala-common-conventions")
    id("gradle.scala-flink-conventions")
}

group = "com.github.derghust.flinkblaze.impl"

dependencies {
    api(project(":operator"))
}
