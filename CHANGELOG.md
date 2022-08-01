# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.1.2] - 2022-08-01
### Added
- Add targets: linuxX64, macosArm64, mingwX64
  - Stop building frameworks, they aren't published by default

### Changed
- Dependency update:
  - [Kotlin 1.7.10](https://github.com/JetBrains/kotlin/releases/tag/v1.7.10)
  - [Gradle-7.5](https://docs.gradle.org/7.5/release-notes.html)
  - [Spotless-6.9.0](https://github.com/diffplug/spotless/blob/main/plugin-gradle/CHANGES.md#690---2022-07-28)

## [1.1.1] - 2022-06-29
### Changed
- Fix Maven Central publishing

## [1.1.0] - 2022-06-29
### Changed
- Android:
  - Support Android 12 [API level 32](https://developer.android.com/studio/releases/platforms#12)
- Dependency update:
  - [Android Gradle Plugin 7.2.1](https://developer.android.com/studio/releases/gradle-plugin#7-2-0)
  - [Kotlin 1.7.0](https://github.com/JetBrains/kotlin/releases/tag/v1.7.0)
  - [Ktor 2.0.3](https://ktor.io/changelog/2.0#version-2-0-3)

## [1.0.6] - 2022-05-04
### Changed
- Dependency update:
  - [Ktor 2.0.1](https://ktor.io/changelog/2.0#version-2-0-1)

## [1.0.5] - 2022-04-26
### Changed
- Dependency update:
  - [Gradle 7.4.2](https://docs.gradle.org/7.4.2/release-notes.html)
  - [Kotlin 1.6.21](https://github.com/JetBrains/kotlin/releases/tag/v1.6.21)
  - [Ktor 2.0.0](https://ktor.io/changelog/2.0#version-2-0-0)

## [1.0.4] - 2022-03-15
### Changed
- Remove String extension methods for the time being

## [1.0.3] - 2022-03-10
### Added
- More extension methods

### Changed
- Dependency update:
  - [Gradle 7.4.1](https://docs.gradle.org/7.4.1/release-notes.html)

## [1.0.2] - 2022-03-10
### Changed
- Gitlab: Publish Android variants, fix iOS simulator tests

## [1.0.1] - 2022-03-10
### Added
- Convenience extension methods

## [1.0.0] - 2022-03-07
### Added
- Initial implementation
