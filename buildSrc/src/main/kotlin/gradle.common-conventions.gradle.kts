plugins {
    scala
    `java-library`
}

repositories {
    mavenCentral()
}

extra["scalaVersion"] = "2.12"
extra["scalaPatchVersion"] = "17"

extra["apacheFlinkVersion"] = "1.17.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
