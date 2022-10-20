package com.bikcodeh.mubi

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MubiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpAppCenter()
    }

    private fun setUpAppCenter() {
        AppCenter.start(
            this,
            BuildConfig.APP_CENTER_KEY,
            Analytics::class.java, Crashes::class.java
        )
    }
}