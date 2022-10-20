plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")
}
android {
    compileSdk = Config.AndroidSdk.compile
    namespace = "com.bikcodeh.mubi.data"
    defaultConfig {
        minSdk = Config.AndroidSdk.minSdk
        targetSdk = Config.AndroidSdk.target
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))
    implementation(Config.Dependencies.Jetpack.coreKtx)
    /** Dagger */
    implementation(Config.Dependencies.DaggerHilt.hiltAndroid)
    kapt(Config.Dependencies.DaggerHilt.hiltCompiler)
    kapt(Config.Dependencies.DaggerHilt.androidXHiltCompiler)
    /** Network */
    implementation(Config.Dependencies.Network.retrofit)
    implementation(Config.Dependencies.Network.okHttp)
    implementation(Config.Dependencies.Network.okHttpLoggingInterceptor)
    implementation(Config.Dependencies.Network.moshi)
    implementation(Config.Dependencies.moshiKotlin)
    /** Room */
    implementation(Config.Dependencies.Jetpack.room)
    kapt(Config.Dependencies.Jetpack.roomCompiler)
    implementation(Config.Dependencies.Jetpack.roomKtx)
}