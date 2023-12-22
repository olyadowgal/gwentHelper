package com.dowgalolya.gwenthelper.application

import android.app.Application
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SingletonHolder.init(this)
        Stetho.initializeWithDefaults(this)
    }
}