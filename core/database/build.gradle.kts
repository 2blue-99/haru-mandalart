plugins {
    alias(libs.plugins.hm.android.library)
    alias(libs.plugins.hm.android.hilt)
}

android {
    namespace = "com.coldblue.database"
    defaultConfig{
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }

    }

}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}