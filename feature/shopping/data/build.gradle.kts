plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.davidmerchan.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            // Exclude duplicated files
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
            excludes.add("META-INF/LICENSE.txt")
            excludes.add("META-INF/NOTICE.md")
            excludes.add("META-INF/NOTICE.txt")
        }
    }
}

dependencies {

    implementation(project(":feature:shopping:domain"))
    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // MockK
    // JUnit 4
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // MockK
    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("io.mockk:mockk-android:1.13.5")
    testImplementation("io.mockk:mockk-agent-jvm:1.13.5")

    // Para correr las pruebas en un ambiente de Android
    testImplementation("androidx.test:core:1.5.0")

    // Coroutines si tu proyecto las usa
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
}