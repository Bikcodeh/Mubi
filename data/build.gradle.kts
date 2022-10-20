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
        val apiKey = "4662e7a7fe13c9d91c80552e10a09dc1"
        buildTypes {
            getByName("debug") {
                buildConfigField(
                    type = "String",
                    name = "API_DB_KEY",
                    value = '"' + apiKey + '"'
                )
            }
            getByName("release") {
                buildConfigField(
                    type = "String",
                    name = "API_DB_KEY",
                    value = '"' + apiKey + '"'
                )
            }
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))
    implementation(Config.Dependencies.Jetpack.coreKtx)
    implementation(Config.Dependencies.Jetpack.paging)
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