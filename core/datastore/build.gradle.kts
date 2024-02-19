plugins {
    alias(libs.plugins.hm.android.library)
    alias(libs.plugins.hm.android.hilt)
}

android {
    namespace = "com.coldblue.datastore"
}

dependencies {

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
}