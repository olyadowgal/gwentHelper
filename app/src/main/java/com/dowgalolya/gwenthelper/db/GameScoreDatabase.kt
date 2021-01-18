package com.dowgalolya.gwenthelper.db

import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [GameScore::class],
    version = 2,
    exportSchema = true
)

abstract class GameScoreDatabase : RoomDatabase() {

    abstract fun gameScoreDao(): GameScoreDao

   @VisibleForTesting
   object MigrationFrom1To2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE game_score ADD COLUMN first_round_first_player INTEGER")
            database.execSQL("ALTER TABLE game_score ADD COLUMN second_round_first_player INTEGER")
            database.execSQL("ALTER TABLE game_score ADD COLUMN third_round_first_player INTEGER")
            database.execSQL("ALTER TABLE game_score ADD COLUMN first_round_second_player INTEGER")
            database.execSQL("ALTER TABLE game_score ADD COLUMN second_round_second_player INTEGER")
            database.execSQL("ALTER TABLE game_score ADD COLUMN third_round_second_player INTEGER")
        }
    }
}