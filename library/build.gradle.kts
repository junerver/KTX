plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("com.vanniktech.maven.publish")
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    api("io.arrow-kt:arrow-core:1.2.0-RC")
    api("androidx.core:core-ktx:1.10.1")
    implementation(kotlin("reflect"))
    //kotlin 协程
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}