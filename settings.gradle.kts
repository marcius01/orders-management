pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/marcius01/framework")
            credentials {
                username = providers.gradleProperty("github.repository.user").orNull
                password = providers.gradleProperty("github.repository.key").orNull
            }
        }
    }

    plugins {
        val quarkusPluginId: String? = providers.gradleProperty("quarkusPluginId").orNull
        val quarkusPluginVersion: String? = providers.gradleProperty("quarkusPluginVersion").orNull
        if (quarkusPluginId != null && quarkusPluginVersion != null) {
            id(quarkusPluginId) version quarkusPluginVersion
        } else {
            println("Attenzione: le proprietà 'quarkusPluginId' o 'quarkusPluginVersion' non sono definite nelle impostazioni Gradle.")
        }
    }
}

// Central configuration for the modules
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/marcius01/framework")
            credentials {
                username = providers.gradleProperty("github.repository.user").orNull
                password = providers.gradleProperty("github.repository.key").orNull
            }
        }
    }
}

//pluginManagement {
//    val quarkusPluginVersion: String by settings
//    val quarkusPluginId: String by settings
//    repositories {
//        mavenCentral()
//        gradlePluginPortal()
//        mavenLocal()
//    }
//    plugins {
//        id(quarkusPluginId) version quarkusPluginVersion
//    }
//}

rootProject.name = "orders-management"
include("ms:ms-products")
include("ms:ms-auth")
include("ms:commons-event")
include("ms:ms-orders")