package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dowgalolya.gwenthelper.adapters.ScoreAdapter
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreViewModel(
    application: Application,
    private val gwentRepository: GwentRepository
) : BaseViewModel(application) {

    val statsAdapter: ScoreAdapter = ScoreAdapter()

    private val _isScoresButtonActive = MutableLiveData(false)
    val isScoresButtonActive: LiveData<Boolean> = _isScoresButtonActive

    init {
        loadDatabaseScores()
    }

    fun loadDatabaseScores() {
        viewModelScope.launch {
            val list: List<GameScore> = gwentRepository.getAllGames()
            statsAdapter.addAll(list)
            _isScoresButtonActive.value = list.isNotEmpty()
        }
    }

    fun clearScores() {
        _isScoresButtonActive.value = false
        viewModelScope.launch { gwentRepository.deleteAllGames() }
        statsAdapter.clearAll()
    }
}