plugins {
    alias(libs.plugins.android.library)
}

fun DependencyHandlerScope.commonDependencies() {
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.androidx.viewmodel)
    implementation(libs.hilt.kotlinx.coroutine)
}
