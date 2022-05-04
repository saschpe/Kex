plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
    signing
}

kotlin {
    android { publishAllLibraryVariants() }
    ios { binaries.framework("Kex") }
    iosSimulatorArm64 { binaries.framework("Kex") }
    js {
        nodejs()
        compilations.all {
            kotlinOptions.sourceMap = true
            kotlinOptions.moduleKind = "umd"
        }
    }
    jvm { testRuns["test"].executionTask.configure { useJUnitPlatform() } }

    sourceSets["commonMain"].dependencies {
        implementation("io.ktor:ktor-io:2.0.1")
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin("test"))
    }
    sourceSets["iosSimulatorArm64Main"].dependsOn(sourceSets["iosMain"])
    sourceSets["iosSimulatorArm64Test"].dependsOn(sourceSets["iosTest"])

    sourceSets.remove(sourceSets["androidAndroidTestRelease"]) // https://issuetracker.google.com/issues/152187160

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests::class.java) {
        testRuns["test"].deviceId = "iPhone 13"
    }
}

android {
    buildToolsVersion = "32.0.0"
    compileSdk = 31

    defaultConfig {
        minSdk = 17
        targetSdk = 31
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    testCoverage.jacocoVersion = "0.8.8"
}

group = "de.peilicke.sascha"
version = "1.0.5"

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        pom {
            name.set("Kex")
            description.set("Hex string encoder/decoder for Kotlin/Multiplatform. Supports Android, iOS, JavaScript and plain JVM environments.")
            url.set("https://github.com/saschpe/kex")

            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://opensource.org/licenses/Apache-2.0")
                }
            }
            developers {
                developer {
                    id.set("saschpe")
                    name.set("Sascha Peilicke")
                    email.set("sascha@peilicke.de")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/saschpe/kex.git")
                developerConnection.set("scm:git:ssh://github.com/saschpe/kex.git")
                url.set("https://github.com/saschpe/kex")
            }
        }
    }

    repositories {
        maven {
            name = "sonatype"
            credentials {
                username = Secrets.Sonatype.user
                password = Secrets.Sonatype.apiKey
            }
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
        }
    }
}

signing {
    val sonatypeGpgKey = System.getenv("SONATYPE_GPG_KEY")
    val sonatypeGpgKeyPassword = System.getenv("SONATYPE_GPG_KEY_PASSWORD")
    when {
        sonatypeGpgKey == null || sonatypeGpgKeyPassword == null -> useGpgCmd()
        else -> useInMemoryPgpKeys(sonatypeGpgKey, sonatypeGpgKeyPassword)
    }
    sign(publishing.publications)
}
