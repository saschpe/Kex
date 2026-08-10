plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
    `maven-publish`
    signing
}

kotlin {
    android {
        namespace = "saschpe.kex"
        minSdk = 21
        compileSdk = 34
    }
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    js { nodejs() }
    jvm()
    macosArm64()
    tvosArm64()
    watchosArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation("io.ktor:ktor-io:3.4.0")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

val javadocJar = tasks.register<Jar>("javadocJar") {
    description = "Assembles Kotlin docs with Javadoc"
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication> {
        groupId = "de.peilicke.sascha"
        version = "1.1.4"

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

    val sonatypeUser = providers.gradleProperty("sonatypeUser")
    val sonatypePass = providers.gradleProperty("sonatypePass")
    if (sonatypeUser.isPresent && sonatypePass.isPresent) {
        repositories {
            maven {
                name = "sonatype"
                credentials {
                    username = sonatypeUser.get()
                    password = sonatypePass.get()
                }
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            }
        }
    }
}

signing {
    val sonatypeGpgKey = providers.systemProperty("SONATYPE_GPG_KEY")
    val sonatypeGpgKeyPassword = providers.systemProperty("SONATYPE_GPG_KEY_PASSWORD")
    if (sonatypeGpgKey.isPresent && sonatypeGpgKeyPassword.isPresent) {
        useInMemoryPgpKeys(sonatypeGpgKey.get(), sonatypeGpgKeyPassword.get())
        sign(publishing.publications)
    }
}
