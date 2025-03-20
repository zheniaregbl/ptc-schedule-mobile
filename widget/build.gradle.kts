import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {

    applyDefaultHierarchyTemplate()

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Widget"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            api(libs.glance.appwidget)
            api(libs.glance.preview)
            api(libs.glance.appwidget.preview)
            api(libs.glance.material3)
        }
        commonMain.dependencies {
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)

            api(libs.kotlinx.serialization.json)

            api(libs.kotlinx.datetime)

            api(libs.koin.compose)
            api(libs.koin.core)

            api(libs.bundles.ktor)

            api(libs.sandwich)
            api(libs.sandwich.ktor)

            api(projects.core)
        }

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.syndicate.ptkscheduleapp.widget"
    generateResClass = auto
}

android {
    namespace = "com.syndicate.ptkscheduleapp.widget"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}