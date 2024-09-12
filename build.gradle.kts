plugins {
    kotlin("jvm") version "1.7.10" apply false
    id("com.android.library") version "7.2.2" apply false
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

spotless {
    freshmark {
        target("**/*.md")
        propertiesFile("gradle.properties")
    }
    kotlin {
        target("**/*.kt")
        ktlint("1.3.1").setEditorConfigPath("${project.rootDir}/.editorconfig")
    }
    kotlinGradle {
        ktlint("1.3.1").setEditorConfigPath("${project.rootDir}/.editorconfig")
    }
}

tasks {
    withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
        rejectVersionIf {
            fun isStable(version: String) = Regex("^[0-9,.v-]+(-r)?$").matches(version)
            !isStable(candidate.version) && isStable(currentVersion)
        }
    }
}
