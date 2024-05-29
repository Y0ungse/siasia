buildscript {
    repositories {
        google()  // Google Maven 저장소 추가
        mavenCentral()
    }
    dependencies {

        classpath("com.android.tools.build:gradle:8.1.1")
        //구글
        classpath("com.google.gms:google-services:4.3.15")


    }
}

//4.3.15
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    // Gradle Plugin for Android 애플리케이션
    id("com.android.application") version "8.1.1" apply false
    //alias(libs.plugins.jetbrainsKotlinAndroid) apply false
   // id("org.jetbrains.kotlin.android") version "1.9.0" apply true
    id("com.google.gms.google-services") version "4.3.15" apply false

}

