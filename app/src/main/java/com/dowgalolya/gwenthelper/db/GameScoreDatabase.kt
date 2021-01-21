package com.dowgalolya.gwenthelper.db

import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [GameScore::class],
    version = 2,
    exportSchema = true
)

@TypeConverters(DateConverter::class)

abstract class GameScoreDatabase : RoomDatabase() {

    abstract fun gameScoreDao(): GameScoreDao

   @VisibleForTesting
   object MigrationFrom1To2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE game_score")
        }
    }
}