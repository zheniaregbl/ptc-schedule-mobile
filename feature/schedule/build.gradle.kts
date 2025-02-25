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
            baseName = "Schedule"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            api(libs.ui.tooling.preview)
        }
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.runtime.compose)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.material)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)

            api(libs.shimmer)

            api(libs.voyager.navigator)
            api(libs.voyager.screenmodel)
            api(libs.voyager.transitions)
            api(libs.voyager.lifecycle.kmp)

            api(libs.kotlinx.serialization.json)

            api(libs.kotlinx.datetime)

            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            api(libs.koin.core)

            api(libs.bundles.ktor)

            api(libs.connectivity.core)
            api(libs.connectivity.compose)

            api(libs.sandwich)
            api(libs.sandwich.ktor)

            api(projects.core)
            api(projects.uiKit.foundations)
        }

        val deviceMain by creating {
            dependsOn(commonMain.get())
            androidMain.get().dependsOn(this)
            iosMain.get().dependsOn(this)
            dependencies {
                api(libs.connectivity.device)
                api(libs.connectivity.compose.device)
            }
        }

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.syndicate.ptkscheduleapp.feature.schedule.resources"
    generateResClass = auto
}

android {
    namespace = "com.syndicate.ptkscheduleapp.feature.schedule"
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

dependencies {
    debugImplementation(compose.uiTooling)
}
