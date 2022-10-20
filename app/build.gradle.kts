import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.bikcodeh.mubi"
    compileSdk = Config.AndroidSdk.compile

    defaultConfig {
        applicationId = "com.bikcodeh.mubi"
        minSdk = Config.AndroidSdk.minSdk
        targetSdk = Config.AndroidSdk.target
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = Config.AndroidSdk.instrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val appCenterKey = "d5f7c121-8b57-469a-818b-5c3aa526e965"
        getByName("debug") {
            buildConfigField(
                type = "String",
                name = "APP_CENTER_KEY",
                value = '"' + appCenterKey + '"'
            )
        }
        getByName("release") {
            buildConfigField(
                type = "String",
                name = "APP_CENTER_KEY",
                value = '"' + appCenterKey + '"'
            )
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
        kotlinCompilerExtensionVersion = Config.Dependencies.Compose.Version.core
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":presentation"))
    /** Dagger */
    implementation(Config.Dependencies.DaggerHilt.hiltAndroid)
    kapt(Config.Dependencies.DaggerHilt.hiltCompiler)
    kapt(Config.Dependencies.DaggerHilt.androidXHiltCompiler)
}

fun getProps(propName: String): String {
    val propsFile = rootProject.file("local.properties")
    return if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        props[propName] as String
    } else {
        ""
    }
}