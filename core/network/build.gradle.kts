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
}

dependencies {

    implementation(libs.supabase.gotrue)
    implementation(libs.ktor.client.cio)
    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.compose.auth.ui)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:model"))
}