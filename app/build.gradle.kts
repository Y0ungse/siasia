plugins {
    //alias(libs.plugins.androidApplication)
    //alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.android.application")
    id("com.google.gms.google-services")  //구글서비스 추가
    id("org.jetbrains.kotlin.android") version "1.9.0" apply true

}


android {
    namespace = "com.example.food_detect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.food_detect"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //implementation 'org.tensorflow:tensorflow-lite:2.4.0'

    //implementation(libs.tensorflow.lite)
    //implementation(libs.tensorflow.lite.gpu)
    //implementation("com.google.firebase:firebase-database:32.3.1")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")




}