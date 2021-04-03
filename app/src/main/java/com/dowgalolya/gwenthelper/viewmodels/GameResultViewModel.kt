package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import com.dowgalolya.gwenthelper.fragments.GameResultFragmentDirections
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.google.android.play.core.review.ReviewManagerFactory

class GameResultViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        const val REVIEW_INFO = "REVIEW_INFO"
        const val REVIEW_MANAGER = "REVIEW_MANAGER"
    }

    object CustomViewAction {
        const val LAUNCH_REVIEW = "LAUNCH_REVIEW"
        const val PLAY_AGAIN = "PLAY_AGAIN"
    }

    fun onExit() {
        _viewAction.value = ViewAction.NavigateWithDirection(GameResultFragmentDirections.actionGameResultFragmentToMainFragment())
    }

    fun onPlay() {
        _viewAction.value = ViewAction.Custom(CustomViewAction.PLAY_AGAIN)
    }

    fun startReviewRequest() {
        val manager = ReviewManagerFactory.create(getApplication<Application>().applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { r ->
            if (r.isSuccessful) {
                val reviewInfo = r.result
                _viewAction.value =
                    ViewAction.Custom(CustomViewAction.LAUNCH_REVIEW)
                        .putArg(REVIEW_MANAGER, manager)
                        .putArg(REVIEW_INFO, reviewInfo)
            }
        }
    }
}