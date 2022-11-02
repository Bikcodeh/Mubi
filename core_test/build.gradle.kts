plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.bikcodeh.mubi.core_test"
    compileSdk = Config.AndroidSdk.compile
    defaultConfig {
        minSdk = Config.AndroidSdk.minSdk
        targetSdk = Config.AndroidSdk.target
    }
}

dependencies {
    api(Config.Dependencies.Test.junit)
    api(Config.Dependencies.Jetpack.lifecycleRuntime)
    api(Config.Dependencies.Test.androidxJunit)
    api(Config.Dependencies.Test.truth)
    api(Config.Dependencies.Test.coroutines)
    api(Config.Dependencies.Test.mockk)
    api(Config.Dependencies.Test.mockkJvm)
}