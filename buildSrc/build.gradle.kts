plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.dev.dszn.cz/content/groups/repo/")
}

dependencies {
    implementation("com.diffplug.spotless:com.diffplug.spotless.gradle.plugin:6.14.0")
    implementation("com.diffplug.spotless-changelog:com.diffplug.spotless-changelog.gradle.plugin:2.4.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
