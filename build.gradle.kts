buildscript {
    dependencies {
        classpath(Config.Dependencies.ClassPath.hiltAndroid)
        classpath(Config.Dependencies.ClassPath.jacoco)
    }
}
plugins {
    id(Config.Plugins.androidApplication) version Config.Plugins.Version.androidApplication apply false
    id(Config.Plugins.androidLibrary) version Config.Plugins.Version.androidApplication apply false
    id(Config.Plugins.kotlinAndroid) version Config.Plugins.Version.kotlinAndroid apply false
}