pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()     // Needed if the 'java-client' jar has been published to maven central.
        mavenLocal()
    }

}


rootProject.name = "bestDiet"
include(":app")
