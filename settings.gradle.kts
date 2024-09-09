pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "1.9.0"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WannaComic"
include(":app")
include(":network")
include(":core")
include(":database")
include(":feature")
include(":feature:home")
include(":feature:home:data")
include(":feature:home:domain")
include(":feature:home:presentation")
include(":feature:home:di")
include(":feature:shopping")
include(":feature:shopping:data")
include(":feature:shopping:domain")
include(":feature:shopping:presentation")
include(":feature:detail")
include(":feature:detail:data")
include(":feature:detail:domain")
include(":feature:detail:presentation")
include(":feature:detail:di")
include(":feature:shopping:di")
