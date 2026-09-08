plugins {
    application
    alias(libs.plugins.javafx)
    alias(libs.plugins.shadow)
}

application {
    mainClass = "lp.games.tetris.Launcher"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

javafx {
    version = libs.versions.javafx.get()
    modules = listOf("javafx.controls")
}

group = "lp.games"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveFileName = "Tetris.jar"
}