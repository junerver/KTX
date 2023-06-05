plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.1")
    api("io.arrow-kt:arrow-core:1.2.0-RC")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.edusoa.android.kotlin"
                artifactId = "ktx"
                version = "0.0.6"
            }
        }
        repositories {
            maven {
                isAllowInsecureProtocol = true
                name = "nexus"
                url = uri("http://10.10.15.120:8081/repository/maven-releases/")
                credentials {
                    username = "admin"
                    password = "admin"
                }
            }
        }
    }
}