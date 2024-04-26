plugins {
    alias(libs.plugins.hm.android.application)
    alias(libs.plugins.hm.android.hilt)
    alias(libs.plugins.hm.android.application.compose)
    id("com.google.android.gms.oss-licenses-plugin")
}
android {
    namespace = "com.coldblue.haru_mandalart"

    defaultConfig {
        applicationId = "com.coldblue.haru_mandalart"
        versionCode = 4
        versionName = "1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
    implementation(project(":feature:todo"))
    implementation(project(":feature:manda"))
    implementation(project(":feature:tutorial"))
    implementation(project(":feature:setting"))
    implementation(project(":feature:history"))
    implementation(project(":feature:login"))
    implementation(project(":feature:notice"))
    implementation(project(":feature:survey"))
    implementation(project(":feature:explain"))


    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
    implementation (libs.play.services.oss.licenses)
    implementation(libs.androidx.core.splashscreen)


}