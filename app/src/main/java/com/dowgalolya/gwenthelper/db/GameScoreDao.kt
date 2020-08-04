package com.dowgalolya.gwenthelper.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameScoreDao {
    @Query("SELECT * FROM game_score")
    suspend fun getGameScores(): List<GameScore>

    @Query("SELECT * FROM game_score WHERE game_date = :gameDate")
    suspend fun getScoreById(gameDate: String): GameScore

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateScore(score: GameScore): Long
}