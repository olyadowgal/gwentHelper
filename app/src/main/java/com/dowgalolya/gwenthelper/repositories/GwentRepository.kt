package com.dowgalolya.gwenthelper.repositories

import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.db.GameScoreDao

class GwentRepository(private val gameScoreDao: GameScoreDao) {

    suspend fun addGame(game: GameScore) {
        gameScoreDao.insert(game)
    }

    suspend fun getAllGames() : List<GameScore>  {
       return gameScoreDao.getAllGameScores()
    }

    suspend fun deleteAllGames() {
        gameScoreDao.deleteAll()
    }

}