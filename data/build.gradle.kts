plugins {
    id(Config.Plugins.androidApplication)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
}

android {
    compileSdk = Config.AndroidSdk.compile
    namespace = "com.bikcodeh.mubi.data"
}

dependencies {
    implementation(Config.Dependencies.Jetpack.coreKtx)
    /** Network */
    implementation(Config.Dependencies.Network.retrofit)
    implementation(Config.Dependencies.Network.okHttp)
    implementation(Config.Dependencies.Network.okHttpLoggingInterceptor)
    implementation(Config.Dependencies.Network.moshi)
    implementation(Config.Dependencies.moshiKotlin)
    /** Dagger */
    implementation(Config.Dependencies.DaggerHilt.hiltAndroid)
    kapt(Config.Dependencies.DaggerHilt.hiltCompiler)
    kapt(Config.Dependencies.DaggerHilt.androidXHiltCompiler)
    /** Room */
    implementation(Config.Dependencies.Jetpack.room)
    kapt(Config.Dependencies.Jetpack.roomCompiler)
    implementation(Config.Dependencies.Jetpack.roomKtx)
}