package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dowgalolya.gwenthelper.fragments.MainFragmentDirections
import com.dowgalolya.gwenthelper.livedata.SingleLiveEvent
import com.dowgalolya.gwenthelper.livedata.ViewAction


class MainViewModel(application: Application) : BaseViewModel(application) {


    private val _selectedUser = SingleLiveEvent<Int>()
    val selectedUser: LiveData<Int> = _selectedUser

    private val _firstUserPhotoUri = MutableLiveData<Uri>()
    val firstUserPhotoUri : LiveData<Uri> = _firstUserPhotoUri

    private val _secondUserPhotoUri = MutableLiveData<Uri>()
    val secondUserPhotoUri : LiveData<Uri> = _secondUserPhotoUri


    fun onPlayClicked(user1Name: String?, user2Name: String?) {
        val direction = MainFragmentDirections.actionMainFragmentToGameFragment()
        if (!user1Name.isNullOrBlank()) {
            direction.user1 = user1Name
        }
        if (!user2Name.isNullOrBlank()) {
            direction.user2 = user2Name
        }
        if (_firstUserPhotoUri.value != null) {
            direction.user1Photo = _firstUserPhotoUri.value.toString()
        }
        if (_secondUserPhotoUri.value != null) {
            direction.user2Photo = _secondUserPhotoUri.value.toString()
        }
        _viewAction.value = ViewAction.NavigateWithDirection(direction)
    }
    fun onScoresClicked() {
        _viewAction.value = ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToStatsFragment())
    }

    fun onPhotoClicked(userId: Int) {
        _selectedUser.value = userId
    }

    fun firstUserPhotoUpdate(uri : Uri) {
        _firstUserPhotoUri.value = uri
    }

    fun secondUserPhotoUpdate(uri: Uri) {
        _secondUserPhotoUri.value = uri
    }
}
