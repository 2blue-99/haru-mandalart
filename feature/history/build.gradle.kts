@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}

android {
    namespace = "com.coldblue.history"
}

dependencies {

}