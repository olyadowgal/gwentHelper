package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.dowgalolya.gwenthelper.fragments.MainFragmentDirections
import com.dowgalolya.gwenthelper.livedata.ViewAction


class MainViewModel(application: Application) : BaseViewModel(application) {


    fun onButtonClicked(user1Name: String?, user2Name: String?) {
        val direction = MainFragmentDirections.actionMainFragmentToGameFragment()
        if (!user1Name.isNullOrBlank()) {
            direction.user1 = user1Name
        }
        if (!user2Name.isNullOrBlank()) {
            direction.user2 = user2Name
        }
        _viewAction.value = ViewAction.NavigateWithDirection(direction)
    }

    fun onPhotoClicked(userId: Int) {

    }
}
