object Config {

    object Plugins {
        object Version {
            const val androidApplication = "7.3.0"
            const val kotlinAndroid = "1.6.10"
        }

        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val androidLibrary = "com.android.library"
        const val kotlinKapt = "kotlin-kapt"
        const val daggerHilt = "dagger.hilt.android.plugin"
        const val jacoco = "jacoco"
    }

    object AndroidSdk {
        const val compile = 33
        const val minSdk = 26
        const val target = 33
        const val versionCode = 1
        const val versionName = "1.0"
        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object Dependencies {
        object Version {
            const val coreKtx = "1.7.0"
            const val daggerHilt = "2.44"
            const val hiltCompiler = "1.0.0"
            const val retrofit = "2.9.0"
            const val okHttpLoggingInterceptor = "4.9.0"
            const val okHttp = "4.9.3"
            const val moshi = "1.9.3"
            const val lottie = "5.2.0"
            const val timber = "5.0.1"
            const val appCenter = "4.3.1"
            const val room = "2.4.3"
            const val lifecycle = "2.5.1"
            const val paging = "3.1.0"
            const val accompanist = "0.26.5-rc"
            const val jacoco = "0.8.7"
            const val coil = "2.2.2"
            const val materialIcons = "1.1.1"
            const val leakCanary = "2.9.1"
            const val dataStore = "1.0.0"
            const val gson = "2.9.1"
        }

        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
        const val lottie = "com.airbnb.android:lottie-compose:${Version.lottie}"
        const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Version.accompanist}"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:${Version.accompanist}"
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Version.accompanist}"
        const val timber = "com.jakewharton.timber:timber:${Version.timber}"
        const val coilImages = "io.coil-kt:coil-compose:${Version.coil}"
        const val materialIcons = "androidx.compose.material:material-icons-extended:${Version.materialIcons}"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Version.leakCanary}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Version.dataStore}"
        const val gson = "com.google.code.gson:gson:${Version.gson}"

        object Jetpack {
            const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
            const val room = "androidx.room:room-runtime:${Version.room}"
            const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
            const val roomKtx = "androidx.room:room-ktx:${Version.room}"
            const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"
            const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
            const val paging = "androidx.paging:paging-runtime-ktx:${Version.paging}"
            const val roomPaging = "androidx.room:room-paging:${Version.room}"
        }

        object AppCenter {
            const val analytics = "com.microsoft.appcenter:appcenter-analytics:${Version.appCenter}"
            const val crashes = "com.microsoft.appcenter:appcenter-crashes:${Version.appCenter}"
        }

        object Compose {

            object Version {
                const val core = "1.1.1"
                const val material = "1.3.0-rc01"
                const val material3 = "1.0.0-rc01"
                const val activity = "1.3.1"
                const val navigation = "2.5.0-rc02"
                const val constraintLayout = "1.0.1"
                const val paging = "1.0.0-alpha15"
                const val lifecycle = "2.6.0-alpha02"
            }

            const val material = "androidx.compose.material:material:${Version.material}"
            const val material3 = "androidx.compose.material3:material3:${Version.material3}"
            const val preview = "androidx.compose.ui:ui-tooling-preview:${Version.core}"
            const val activity = "androidx.activity:activity-compose:${Version.activity}"
            const val navigation = "androidx.navigation:navigation-compose:${Version.navigation}"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Version.constraintLayout}"
            const val paging = "androidx.paging:paging-compose:${Version.paging}"
            const val ui = "androidx.compose.ui:ui:${Version.core}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${Version.core}"
            const val lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:${Version.lifecycle}"

        }

        object DaggerHilt {
            const val hiltAndroid = "com.google.dagger:hilt-android:${Version.daggerHilt}"
            const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Version.hiltCompiler}"
            const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.daggerHilt}"
            const val androidXHiltCompiler = "androidx.hilt:hilt-compiler:${Version.hiltCompiler}"
        }

        object Network {
            const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
            const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okHttpLoggingInterceptor}"
            const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
            const val moshi = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
        }

        object ClassPath {
            const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Version.daggerHilt}"
            const val jacoco = "org.jacoco:org.jacoco.core:${Version.jacoco}"
        }
        object Test {
            object Version {
                const val coreTesting = "2.1.0"
                const val junit = "4.13.2"
                const val androidxJunit = "1.1.3"
                const val espresso = "3.4.0"
                const val truth = "1.1.3"
                const val coroutines = "1.6.3"
                const val mockk = "1.12.4"
                const val canaryLeak = "2.9.1"
                const val hiltAndroid = "2.42"
                const val mockWebServer = "4.10.0"
            }

            const val junit = "junit:junit:${Version.junit}"
            const val androidxJunit = "androidx.test.ext:junit:${Version.androidxJunit}"
            const val coreTesting = "androidx.arch.core:core-testing:${Version.coreTesting}"
            const val truth = "com.google.truth:truth:${Version.truth}"
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
            const val mockk = "io.mockk:mockk:${Version.mockk}"
            const val mockkJvm = "io.mockk:mockk:${Version.mockk}"
            const val canaryLeak = "com.squareup.leakcanary:leakcanary-android:${Version.canaryLeak}"
            const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
            const val hiltAndroid = "com.google.dagger:hilt-android-testing:${Version.hiltAndroid}"
            const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hiltAndroid}"
            const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.mockWebServer}"
            const val composeUiJunit = "androidx.compose.ui:ui-test-junit4:${Compose.Version.core}"
            const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Compose.Version.core}"
            const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.Version.core}"
        }
    }
}