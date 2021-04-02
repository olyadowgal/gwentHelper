package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.annotation.MainThread
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.google.android.play.core.review.ReviewManagerFactory

class GameResultViewModel(application: Application) : BaseViewModel(application) {

    object CustomViewAction {
        const val LAUNCH_REVIEW = "LAUNCH_REVIEW"
    }


    fun startReviewRequest() {
        val manager = ReviewManagerFactory.create(getApplication<Application>().applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { r ->
            if (r.isSuccessful) {
                val reviewInfo = r.result
                _viewAction.value =
                    ViewAction.Custom(CustomViewAction.LAUNCH_REVIEW)
                        .putArg(GameViewModel.REVIEW_MANAGER, manager)
                        .putArg(GameViewModel.REVIEW_INFO, reviewInfo)
            }
        }
    }
}