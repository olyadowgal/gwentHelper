package com.dowgalolya.gwenthelper.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameScoreDao {
    @Query("SELECT * FROM game_score")
    suspend fun getAllGameScores(): List<GameScore>

    @Query("SELECT * FROM game_score WHERE date = :gameDate")
    suspend fun getGameScoreById(gameDate: String): GameScore

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: GameScore)
}