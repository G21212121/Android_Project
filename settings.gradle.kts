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
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BoulderingApp"

// Moduł aplikacji Android
include(":app")

// Moduł backendu – odkomentuj gdy będzie gotowy
// include(":backend")

// Moduł współdzielony (np. modele danych) – odkomentuj gdy będzie gotowy
// include(":shared")
