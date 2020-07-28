plugins {
    kotlin("jvm") version "1.3.71"
    id("org.jetbrains.dokka") version "0.10.1"
    application
    java
}

group = "tech.devcord.autohelp"
version = "0.0.1"

/**
 * Used KTor version.
 */
val ktorVersion: String = "1.3.2"

repositories {
    jcenter()
    maven("https://kotlin.bintray.com/ktor")
}

dependencies {

    // Util
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.10.4")
    implementation("com.google.cloud", "google-cloud-vision", "1.99.3")

    //Ktor
    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("io.ktor", "ktor-server-core", ktorVersion)
    implementation("io.ktor", "ktor-jackson", ktorVersion)
    implementation("io.ktor", "ktor-auth", ktorVersion)

    // Logging
    implementation("org.slf4j", "slf4j-api", "2.0.0-alpha1")
    implementation("ch.qos.logback", "logback-classic", "1.3.0-alpha5")
    implementation("io.github.microutils", "kotlin-logging", "1.7.9")

    //Kotlin
    implementation(kotlin("stdlib-jdk8"))

}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "13"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            languageVersion = "1.3"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "13"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            languageVersion = "1.3"
        }
    }

    test {
        useJUnitPlatform()
    }

    dokka {
        outputFormat = "markdown"
        outputDirectory = "docs"

        configuration {
            moduleName = "api"
            includeNonPublic = false
            skipDeprecated = false
            reportUndocumented = true
            skipEmptyPackages = true
            targets = targets + "JVM"
            platform = "JVM"

            sourceLink {
                path = "./"

                url = "https://bitbucket.org/LinkDesk/automator-cloud/src/master/"

                lineSuffix = "#L"
            }

            jdkVersion = 8 // actiaööy java 14 but oracle broke docs since java 10 or something
        }
    }

}

application {
    mainClassName = "com.linkdesk.automator.cloud.ApplicationKt"
}
