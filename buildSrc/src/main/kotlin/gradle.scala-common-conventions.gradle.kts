plugins {
    scala
    `java-library`

    id("gradle.common-conventions")
}

val scalaVersion = project.extra["scalaVersion"] as String
val scalaPatchVersion = project.extra["scalaPatchVersion"] as String
val catsVersion = project.extra["catsVersion"] as String

dependencies {
    api("org.scala-lang:scala-library:${scalaVersion}.${scalaPatchVersion}")

    implementation("org.typelevel:cats-core_${scalaVersion}:${catsVersion}")

    // Use Scalatest for testing our library
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.scalatest:scalatest_${scalaVersion}:3.2.14")
    testImplementation("org.scalatestplus:junit-4-13_${scalaVersion}:3.2.2.0")

    // Need scala-xml at test runtime
    testRuntimeOnly("org.scala-lang.modules:scala-xml_${scalaVersion}:1.2.0")
}
