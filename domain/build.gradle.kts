plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")
}

android {
    namespace = "com.bikcodeh.mubi.domain"
    compileSdk = Config.AndroidSdk.compile
    defaultConfig {
        minSdk = Config.AndroidSdk.minSdk
        targetSdk = Config.AndroidSdk.target
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(Config.Dependencies.Jetpack.coreKtx)
    /** Dagger */
    implementation(Config.Dependencies.DaggerHilt.hiltAndroid)
    kapt(Config.Dependencies.DaggerHilt.hiltCompiler)
    kapt(Config.Dependencies.DaggerHilt.androidXHiltCompiler)
    /** Timber */
    implementation(Config.Dependencies.timber)
    /** Lifecycle */
    implementation(Config.Dependencies.Jetpack.lifecycleRuntime)
    /** App center*/
    implementation(Config.Dependencies.AppCenter.crashes)
}