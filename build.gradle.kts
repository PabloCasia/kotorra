import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("com.android.library")
    id("maven-publish")
}

group = "com.pablocasia.kotorra"
version = "1.0.0"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "kotorra"
            }
        }
    }
    js {
        browser()
    }
    jvm()

    val commonMain by sourceSets.getting {
        dependencies {
            implementation(kotlin("stdlib-common"))
        }
    }
    val commonTest by sourceSets.getting {
        dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }
    }
    val androidMain by sourceSets.getting {
        dependencies {
            implementation(kotlin("stdlib-jdk7"))
        }
    }
    val androidTest by sourceSets.getting
    val iosMain by sourceSets.getting
    val iosTest by sourceSets.getting
    val jsMain by sourceSets.getting {
        dependencies {
            implementation(kotlin("stdlib-js"))
        }
    }
    val jsTest by sourceSets.getting {
        dependencies {
            implementation(kotlin("test-js"))
        }
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(kotlin("stdlib-jdk7"))
        }
    }
    val jvmTest by sourceSets.getting
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(15)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.pablocasia.kotorra"
                artifactId = "kotorra-android"
                version = "1.0.0"
                artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")

                pom {
                    withXml {
                        val dependenciesNode = asNode().appendNode("dependencies")

                        listOf(
                            Pair("compile", "compile"),
                            Pair("api", "api"),
                            Pair("implementation", "runtime")
                        ).forEach { scope ->
                            configurations.findByName(scope.first)?.dependencies
                                ?.filterIsInstance<DefaultExternalModuleDependency>()
                                ?.forEach { dependency ->
                                    dependenciesNode.appendNode("dependency").apply {
                                        appendNode("groupId", dependency.group)
                                        appendNode("artifactId", dependency.name)
                                        appendNode("version", dependency.version)
                                        appendNode("scope", scope.second)
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}
