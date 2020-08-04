package com.dowgalolya.gwenthelper.application

import android.app.Application
import com.dowgalolya.gwenthelper.di.SingletonHolder

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SingletonHolder.init(this)
    }
}