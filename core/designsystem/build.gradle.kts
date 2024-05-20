plugins {
    alias(libs.plugins.hm.android.library)
    alias(libs.plugins.hm.android.library.compose)
}

android {
    namespace = "com.coldblue.designsystem"
}

dependencies {
    debugApi(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
}