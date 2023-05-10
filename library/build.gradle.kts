plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])
                groupId = "com.edusoa.android.kotlin"
                artifactId = "ktx"
                version = "0.0.1"
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