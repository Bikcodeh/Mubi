plugins {
    id(Config.Plugins.androidApplication)
    id(Config.Plugins.kotlinAndroid)
}

android {
    namespace = "com.bikcodeh.mubi"
    compileSdk = Config.AndroidSdk.compile

    defaultConfig {
        applicationId = "com.bikcodeh.mubi"
        minSdk = Config.AndroidSdk.minSdk
        targetSdk = Config.AndroidSdk.target
        versionCode = Config.AndroidSdk.versionCode
        versionName = Config.AndroidSdk.versionName

        testInstrumentationRunner = Config.AndroidSdk.instrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {

        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Config.Dependencies.Jetpack.coreKtx)
    implementation(Config.Dependencies.Jetpack.lifecycleRuntime)
    implementation(Config.Dependencies.Jetpack.lifecycleViewModel)
    implementation(Config.Dependencies.Compose.activity)
    implementation(Config.Dependencies.Compose.ui)
    implementation(Config.Dependencies.Compose.material3)
    implementation(Config.Dependencies.Compose.material)
    implementation(Config.Dependencies.Compose.preview)
    implementation(Config.Dependencies.Compose.constraintLayout)
    implementation(Config.Dependencies.Compose.navigation)
    implementation(Config.Dependencies.Compose.paging)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.1")
}