plugins {
    alias(libs.plugins.hm.android.library)
    alias(libs.plugins.hm.android.hilt)
    alias(libs.plugins.secrets)
    alias(libs.plugins.kotlin.serialization)
}

android {
    buildFeatures{
        buildConfig = true
    }
    namespace = "com.coldblue.network"


    buildTypes {
        release {
            val key = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("RELEASE_URL")
            val url = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("RELEASE_KEY")
            val googleClientId = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("RELEASE_CLIENT_ID")

            buildConfigField("String", "SupaUrl", "\"$key\"")
            buildConfigField("String", "SupaKey", "\"$url\"")
            buildConfigField("String", "ClientId", "\"$googleClientId\"")
        }
        debug {
            val key = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("DEBUG_URL")
            val url = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("DEBUG_KEY")
            val googleClientId = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("DEBUG_CLIENT_ID")
            buildConfigField("String", "SupaUrl", "\"$key\"")
            buildConfigField("String", "SupaKey", "\"$url\"")
            buildConfigField("String", "ClientId", "\"$googleClientId\"")
        }
    }

}

dependencies {

    implementation(libs.ktor.client.cio)
    implementation(libs.supabase.gotrue)
    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.compose.auth.ui)
    implementation(libs.kotlinx.serialization.json)
}