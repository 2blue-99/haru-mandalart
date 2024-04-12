plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}
android {
    namespace = "com.colddelight.mandalart"
}

dependencies {
    implementation("com.google.android.play:app-update-ktx:2.1.0")
}