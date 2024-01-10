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
    implementation("androidx.appcompat:appcompat:1.6.1")
    api("io.arrow-kt:arrow-core:1.2.0-RC")
    api("androidx.core:core-ktx:1.10.1")
    implementation(kotlin("reflect"))
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "xyz.junerver.kotlin"
                artifactId = "ktx"
                version = "0.0.7"
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