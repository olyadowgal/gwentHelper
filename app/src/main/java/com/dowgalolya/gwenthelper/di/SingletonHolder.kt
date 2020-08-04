package com.dowgalolya.gwenthelper.di

import android.app.Application
import android.content.Context
import com.dowgalolya.gwenthelper.db.GameScoreDatabase

object SingletonHolder {

    lateinit var application: Application
    private val context: Context get() = application
    lateinit var db: GameScoreDatabase

    fun init(application: Application) {
        this.application = application
        db = GameScoreDatabase.getInstance(context)
    }

}