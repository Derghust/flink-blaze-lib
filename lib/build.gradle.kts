/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Scala library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.1.1/userguide/building_java_projects.html
 */

plugins {
    // Apply the scala Plugin to add support for Scala.
    scala

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    id("com.diffplug.spotless") version "6.16.0"
    id("com.diffplug.spotless-changelog") version "3.0.2"
}

spotlessChangelog {
    changelogFile("../CHANGELOG.md")
}

// In release date use spotless changelog
//version = spotlessChangelog.versionLast
version = "0.1.0"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val scalaVersion = "2.12"
val scalaPatchVersion = "17"
val apacheFlinkVersion = "1.17.1"

dependencies {
    // Use Scala 2.13 in our library project
    api("org.scala-lang:scala-library:${scalaVersion}.${scalaPatchVersion}")

    // Apache Flink
    api("org.apache.flink:flink-streaming-scala_${scalaVersion}:${apacheFlinkVersion}")

    // Use Scalatest for testing our library
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.scalatest:scalatest_${scalaVersion}:3.2.14")
    testImplementation("org.scalatestplus:junit-4-13_${scalaVersion}:3.2.2.0")

    // Need scala-xml at test runtime
    testRuntimeOnly("org.scala-lang.modules:scala-xml_${scalaVersion}:1.2.0")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

spotless {
    scala {
        // version and configFile, scalaMajorVersion are all optional
        scalafmt("3.7.4").configFile("../.scalafmt.conf").scalaMajorVersion(scalaVersion)
    }
}
