buildscript {
    val kotlin_version by extra("1.4.21")
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("com.google.gms:google-services:4.3.4")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
    }
}
group = "org.itmodreamteam.myrest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}
