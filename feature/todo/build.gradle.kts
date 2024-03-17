plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}
android {
    namespace = "com.coldblue.todo"
}

dependencies {
    implementation("com.google.code.gson:gson:2.10")
}