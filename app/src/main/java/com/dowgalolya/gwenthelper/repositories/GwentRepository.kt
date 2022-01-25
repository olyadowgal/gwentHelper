package com.dowgalolya.gwenthelper.repositories

import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.db.GameScoreDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GwentRepository(private val gameScoreDao: GameScoreDao) {

    suspend fun addGame(game: GameScore) {
        gameScoreDao.insert(game)
    }

    suspend fun getAllGames() : List<GameScore> = withContext(Dispatchers.IO) {
       gameScoreDao.getAllGameScores()
    }

    suspend fun deleteAllGames() {
        gameScoreDao.deleteAll()
    }
}