plugins {
    scala
    `java-library`

    id("com.diffplug.spotless")
    id("com.diffplug.spotless-changelog")

    id("gradle.common-conventions")
}

val scalaVersion = project.extra["scalaVersion"] as String

spotless {
    scala {
        // version and configFile, scalaMajorVersion are all optional
        scalafmt("3.7.4").configFile("../.scalafmt.conf").scalaMajorVersion(scalaVersion)
    }
}

spotlessChangelog {
    changelogFile("../CHANGELOG.md")
}

version = spotlessChangelog.versionLast

tasks.jar {
    archiveFileName.set("flinkblaze-${project.name}-${project.version}.jar")
}
