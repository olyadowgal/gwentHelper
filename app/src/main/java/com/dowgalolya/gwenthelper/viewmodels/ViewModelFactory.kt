package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dowgalolya.gwenthelper.repositories.GwentRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(
    val application: Application,
    private val gwentRepository: GwentRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> {
                GameViewModel(application, gwentRepository) as T
            }
            MainViewModel::class.java -> {
                MainViewModel(application) as T
            }
            StatsViewModel::class.java -> {
                StatsViewModel(application, gwentRepository) as T
            }
            else -> throw IllegalArgumentException()
        }
    }
}