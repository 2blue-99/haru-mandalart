plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}
android {
    namespace = "com.coldblue.feature.history"
}

dependencies {
    implementation(project(":feature:todo"))


}