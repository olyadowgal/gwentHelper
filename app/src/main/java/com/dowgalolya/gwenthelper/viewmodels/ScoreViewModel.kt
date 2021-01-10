package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import com.dowgalolya.gwenthelper.adapters.ScoreAdapter
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreViewModel(application: Application, private val gwentRepository: GwentRepository) :
    BaseViewModel(application) {

    val statsAdapter: ScoreAdapter = ScoreAdapter()

    object  CustomViewAction {
        const val HIDE_RESET_SCORES_BUTTON = "HIDE_RESET_SCORES_BUTTON"
        const val SHOW_RESET_SCORES_BUTTON = "SHOW_RESET_SCORES_BUTTON"
    }


    fun loadDatabaseScores() {
        GlobalScope.launch {
            val list : MutableList<GameScore> = gwentRepository.getAllGames().toMutableList()
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _viewAction.value = ViewAction.Custom(CustomViewAction.HIDE_RESET_SCORES_BUTTON)
                } else {
                    _viewAction.value = ViewAction.Custom(CustomViewAction.SHOW_RESET_SCORES_BUTTON)
                }
                list.forEach {
                    statsAdapter.add(it)
                }
            }
        }
    }

    fun clearScores() {
        _viewAction.value = ViewAction.Custom(CustomViewAction.HIDE_RESET_SCORES_BUTTON)
        GlobalScope.launch { gwentRepository.deleteAllGames() }
        statsAdapter.clearAll()
    }
}