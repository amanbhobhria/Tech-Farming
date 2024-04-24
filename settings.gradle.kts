pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {  url = uri("https://jitpack.io")}
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TechFarming"
include(":app")
 