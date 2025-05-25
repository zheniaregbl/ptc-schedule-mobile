import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.build.config)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
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
            baseName = "Core"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.runtime.compose)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)

            implementation(libs.material.icons.core)

            api(libs.datastore)
            api(libs.datastore.preferences)

            api(libs.kotlinx.serialization.json)

            api(libs.kotlinx.datetime)

            api(libs.voyager.navigator)

            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            api(libs.koin.core)

            api(libs.sandwich)
            api(libs.sandwich.ktor)

            api(libs.bundles.ktor)
        }
    }
}

val localProperties = project.rootProject.file("local.properties")
val properties = Properties()
if (localProperties.exists()) {
    properties.load(localProperties.inputStream())
}

val baseUrl = properties.getProperty("BASE_URL") ?: ""
val ruStoreApiUrl = properties.getProperty("RUSTORE_API_URL") ?: ""
val ruStoreAppUrl = properties.getProperty("RUSTORE_APP_URL") ?: ""
val keyId = properties.getProperty("KEY_ID") ?: ""
val keyContent = properties.getProperty("KEY_CONTENT") ?: ""

buildConfig {
    buildConfigField("BASE_URL", baseUrl)
    buildConfigField("RUSTORE_API_URL", ruStoreApiUrl)
    buildConfigField("RUSTORE_APP_URL", ruStoreAppUrl)
    buildConfigField("KEY_ID", keyId)
    buildConfigField("KEY_CONTENT", keyContent)
}

android {
    namespace = "com.syndicate.ptkscheduleapp.core"
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
