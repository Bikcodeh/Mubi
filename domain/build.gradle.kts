plugins {
    id(Config.Plugins.androidApplication)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
}

android {
    namespace = "com.bikcodeh.mubi.domain"
    compileSdk = Config.AndroidSdk.compile
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