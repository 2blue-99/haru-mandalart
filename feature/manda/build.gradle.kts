plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}
android {
    namespace = "com.colddelight.mandalart"
}

dependencies {
    implementation(libs.playStore.update)
}