plugins {
    alias(libs.plugins.hm.android.feature)
    alias(libs.plugins.hm.android.library.compose)
}
android {
    namespace = "com.coldblue.todo"
}

dependencies {
    implementation("com.google.code.gson:gson:2.10")
    implementation( "androidx.glance:glance-appwidget:1.1.0-rc01" )
    implementation( "androidx.glance:glance-material3:1.1.0-rc01" )
}