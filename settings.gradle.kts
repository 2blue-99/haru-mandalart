pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "haru-mandalart"
include(":app")

include(":core:data")
include(":core:datastore")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:designsystem")




include(":feature:todo")
include(":feature:mandalart")
include(":feature:tutorial")
include(":feature:setting")
include(":feature:history")
include(":feature:login")
