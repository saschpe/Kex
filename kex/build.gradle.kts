plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("com.vanniktech.maven.publish") version "0.37.0"
}

kotlin {
    jvmToolchain(21)

    android {
        namespace = "saschpe.kex"
        minSdk = 21
        compileSdk = 37
        withHostTest { isIncludeAndroidResources = true }
    }
    iosArm64()
    iosSimulatorArm64()
    js { nodejs() }
    jvm()
    macosArm64()
    tvosArm64()
    watchosArm64()

    sourceSets {
        commonMain.dependencies { implementation("io.ktor:ktor-io:3.6.1") }
        commonTest.dependencies { implementation(kotlin("test")) }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates("de.peilicke.sascha", name, version.toString())

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
