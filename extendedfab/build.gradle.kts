@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    id("com.vanniktech.maven.publish") version "0.30.0"
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates("io.github.agent-polyblank", "extendedfab", "1.2.0")

    pom {
        name.set("Extended FAB")
        description.set("A multiplatform extended FAB library")
        inceptionYear.set("2025")
        url.set("https://github.com/agent-polyblank/ComposeExtendedFab")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/agent-polyblank/ComposeExtendedFab/blob/develop/LICENSE.txt")
                distribution.set("https://github.com/agent-polyblank/ComposeExtendedFab/develop/LICENSE.txt")
            }
        }
        developers {
            developer {
                id.set("agent-polyblank")
                name.set("Sam King")
                url.set("https://github.com/agent-polyblank")
            }
        }
        scm {
            url.set("https://github.com/agent-polyblank/ComposeExtendedFab/")
            connection.set("scm:git:git://github.com/agent-polyblank/ComposeExtendedFab.git")
            developerConnection.set("scm:git:ssh://git@github.com/agent-polyblank/ComposeExtendedFab.git")
        }
    }
}

kotlin {
    jvmToolchain(21)
    androidTarget {
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    js(IR) {
        browser()
        binaries.executable()
    }

    jvm()

    wasmJs {
        browser()
        binaries.executable()
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

    }
}

android {
    namespace = "io.github.agentpolyblank.extendefab"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
}

