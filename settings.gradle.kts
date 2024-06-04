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
include(":feature:manda")
include(":feature:tutorial")
include(":feature:setting")
include(":feature:login")
include(":feature:notice")
include(":feature:survey")
include(":feature:explain")
include(":feature:history")
