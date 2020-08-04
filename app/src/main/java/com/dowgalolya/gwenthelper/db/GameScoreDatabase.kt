package com.dowgalolya.gwenthelper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE = "game_scores"

@Database(
    entities = [GameScore::class],
    version = 1,
    exportSchema = false
)

abstract class GameScoreDatabase : RoomDatabase() {

    abstract fun gameScoresDao(): GameScoreDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: GameScoreDatabase? = null

        fun getInstance(context: Context): GameScoreDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GameScoreDatabase {
            return Room.databaseBuilder(context, GameScoreDatabase::class.java, DATABASE)
                .build()
        }
    }
}