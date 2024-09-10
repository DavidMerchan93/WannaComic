import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.davidmerchan.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")

        // Load File .properties
        val properties = Properties()
        val configFile = file("config.properties") // Asegúrate de usar la ruta correcta

        if (configFile.exists()) {
            properties.load(configFile.inputStream())
        } else {
            throw FileNotFoundException("Properties file not found: $configFile")
        }
        buildConfigField("String", "MARVEL_URL", "\"${properties["MARVEL_URL"]}\"")
        buildConfigField("String", "MARVEL_ENCODE_URL", "\"${properties["MARVEL_ENCODE_URL"]}\"")
        buildConfigField("String", "MARVEL_PUBLIC_KEY", "\"${properties["MARVEL_PUBLIC_KEY"]}\"")
        buildConfigField("String", "MARVEL_PRIVATE_KEY", "\"${properties["MARVEL_PRIVATE_KEY"]}\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Ktor client
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.ktor.client.logging)
    implementation(libs.ktor.ktor.client.content.negotiation)
    implementation(libs.io.ktor.ktor.serialization.kotlinx.json)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.androidx.viewmodel)
    implementation(libs.hilt.kotlinx.coroutine)
}