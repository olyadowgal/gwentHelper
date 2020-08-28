package com.dowgalolya.gwenthelper.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dowgalolya.gwenthelper.db.GameScoreDatabase

object SingletonHolder {

    lateinit var application: Application
    private val context: Context get() = application
    private val appDatabase by lazy {
        Room.databaseBuilder(
            application,
            GameScoreDatabase::class.java,
            "database"
        ).build()
    }

    fun init(application: Application) {
        this.application = application
    }

}