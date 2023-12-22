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
            database.execSQL("CREATE TABLE IF NOT EXISTS `game_score` (`date` INTEGER NOT NULL, `first_player` TEXT NOT NULL, `second_player` TEXT NOT NULL, `winner` TEXT NOT NULL, `first_round_first_player` INTEGER, `second_round_first_player` INTEGER, `third_round_first_player` INTEGER, `first_round_second_player` INTEGER, `second_round_second_player` INTEGER, `third_round_second_player` INTEGER, PRIMARY KEY(`date`))")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_game_score_date` ON `game_score` (`date`)")
        }
    }
}