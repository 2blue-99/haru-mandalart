plugins {
    alias(libs.plugins.hm.android.library)
    alias(libs.plugins.hm.android.hilt)
}

android {
    namespace = "com.coldblue.database"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

}