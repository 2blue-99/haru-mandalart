plugins {
    alias(libs.plugins.hm.android.application)
    alias(libs.plugins.hm.android.hilt)
    alias(libs.plugins.hm.android.application.compose)
}
android {
    namespace = "com.coldblue.haru_mandalart"

    defaultConfig {
        applicationId = "com.coldblue.haru_mandalart"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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


    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
}