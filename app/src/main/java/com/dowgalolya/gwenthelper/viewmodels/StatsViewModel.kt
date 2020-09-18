package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import com.dowgalolya.gwenthelper.adapters.StatsAdapter
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class StatsViewModel(application: Application, private val gwentRepository: GwentRepository) :
    BaseViewModel(application) {

    val statsAdapter: StatsAdapter = StatsAdapter()


    fun loadDatabaseScores() {
        GlobalScope.launch {
            val list : MutableList<GameScore> = gwentRepository.getAllGames().toMutableList()
            withContext(Dispatchers.Main) {
                list.forEach {
                    statsAdapter.add(it)
                }
            }
        }
    }

    fun clearScores() {
        GlobalScope.launch { gwentRepository.deleteAllGames() }
        statsAdapter.clearAll()
    }
}