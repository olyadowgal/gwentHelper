package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dowgalolya.gwenthelper.adapters.ScoreAdapter
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreViewModel(application: Application, private val gwentRepository: GwentRepository) :
    BaseViewModel(application) {

    val statsAdapter: ScoreAdapter = ScoreAdapter()


    object  CustomViewAction {
        const val HIDE_RESET_SCORES_BUTTON = "HIDE_RESET_SCORES_BUTTON"
        const val SHOW_RESET_SCORES_BUTTON = "SHOW_RESET_SCORES_BUTTON"
    }


    fun loadDatabaseScores() {
        viewModelScope.launch {
            val list : List<GameScore> = gwentRepository.getAllGames()
                if (list.isEmpty()) {
                    _viewAction.value = ViewAction.Custom(CustomViewAction.HIDE_RESET_SCORES_BUTTON)
                } else {
                    _viewAction.value = ViewAction.Custom(CustomViewAction.SHOW_RESET_SCORES_BUTTON)
                }
            statsAdapter.addAll(list)

        }
    }

    fun clearScores() {
        _viewAction.value = ViewAction.Custom(CustomViewAction.HIDE_RESET_SCORES_BUTTON)
        GlobalScope.launch { gwentRepository.deleteAllGames() }
        statsAdapter.clearAll()
    }
}