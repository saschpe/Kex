plugins {
    kotlin("jvm") version "2.2.0" apply false
    id("com.android.library") version "8.7.3" apply false
    id("com.diffplug.spotless") version "7.1.0"
    id("com.github.ben-manes.versions") version "0.52.0"
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

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        fun isStable(version: String) = Regex("^[0-9,.v-]+(-r)?$").matches(version)
        !isStable(candidate.version) && isStable(currentVersion)
    }
}
