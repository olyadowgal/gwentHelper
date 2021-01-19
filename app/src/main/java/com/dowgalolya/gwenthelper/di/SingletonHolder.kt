package com.dowgalolya.gwenthelper.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dowgalolya.gwenthelper.db.GameScoreDatabase
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import com.dowgalolya.gwenthelper.viewmodels.ViewModelFactory

object SingletonHolder {

    lateinit var application: Application
    private val context: Context get() = application

    private val appDatabase by lazy {
        Room.databaseBuilder(
            application,
            GameScoreDatabase::class.java,
            "database"
        ).addMigrations(GameScoreDatabase.MigrationFrom1To2)
            .build()
    }

    private val gwentRepository: GwentRepository by lazy {
        GwentRepository(
            gameScoreDao = appDatabase.gameScoreDao()
        )
    }

    val viewModelFactory by lazy {
        ViewModelFactory(application, gwentRepository)
    }

    fun init(application: Application) {
        this.application = application
    }

}