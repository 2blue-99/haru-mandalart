plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}
android {
    namespace = "com.coldblue.login"
}

dependencies {
    implementation(libs.supabase.compose.auth)
    implementation(project(":feature:explain"))
}