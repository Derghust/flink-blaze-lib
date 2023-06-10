plugins {
    id("gradle.common-conventions")
}

val scalaVersion = project.extra["scalaVersion"] as String
val apacheFlinkVersion = project.extra["apacheFlinkVersion"] as String

dependencies {
    api("org.apache.flink:flink-streaming-scala_${scalaVersion}:${apacheFlinkVersion}")
    api("org.apache.flink:flink-metrics-dropwizard:${apacheFlinkVersion}")
}
