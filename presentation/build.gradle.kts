plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")
}

android {
    namespace = "com.bikcodeh.mubi.presentation"
    compileSdk = Config.AndroidSdk.compile
    defaultConfig {
        minSdk = Config.AndroidSdk.minSdk
        targetSdk = Config.AndroidSdk.target
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.Dependencies.Compose.Version.core
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(Config.Dependencies.Jetpack.coreKtx)
    /** Dagger */
    implementation(Config.Dependencies.DaggerHilt.hiltAndroid)
    kapt(Config.Dependencies.DaggerHilt.hiltCompiler)
    kapt(Config.Dependencies.DaggerHilt.androidXHiltCompiler)
    implementation(Config.Dependencies.DaggerHilt.hiltNavigation)
    /** Lifecycle */
    implementation(Config.Dependencies.Jetpack.lifecycleRuntime)
    implementation(Config.Dependencies.Jetpack.lifecycleViewModel)
    /** Compose */
    implementation(Config.Dependencies.Compose.ui)
    implementation(Config.Dependencies.Compose.navigation)
    implementation(Config.Dependencies.Compose.constraintLayout)
    implementation(Config.Dependencies.Compose.lifecycle)
    implementation(Config.Dependencies.Compose.paging)
    debugImplementation(Config.Dependencies.Compose.uiTooling)
    api(Config.Dependencies.Compose.material3)
    api(Config.Dependencies.Compose.preview)
    api(Config.Dependencies.Compose.activity)
    api(Config.Dependencies.Compose.material)
    /** Lottie */
    implementation(Config.Dependencies.lottie)
    /** Accompanist */
    api(Config.Dependencies.navigationAnimation)
    api(Config.Dependencies.placeholder)
    api(Config.Dependencies.systemUiController)
    /** Coil */
    implementation(Config.Dependencies.coilImages)
    /** Icons */
    implementation(Config.Dependencies.materialIcons)
    /** App Center */
    api(Config.Dependencies.AppCenter.crashes)
    api(Config.Dependencies.AppCenter.analytics)
    /** Timber */
    api(Config.Dependencies.timber)
}