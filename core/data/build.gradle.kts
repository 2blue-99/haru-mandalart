plugins {
    alias(libs.plugins.hm.android.library)
    alias(libs.plugins.hm.android.hilt)
}

android {
    namespace = "com.coldblue.data"
}

dependencies {


    implementation(project(":core:datastore"))
    implementation(project(":core:database"))

    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
    implementation(libs.supabase.compose.auth)
}