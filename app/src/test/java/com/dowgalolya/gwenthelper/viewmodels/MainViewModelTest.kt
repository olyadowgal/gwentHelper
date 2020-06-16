package com.dowgalolya.gwenthelper.viewmodels

import android.app.Application
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dowgalolya.gwenthelper.fragments.MainFragmentDirections
import com.dowgalolya.gwenthelper.livedata.ViewAction
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest() {

    @get:Rule
   var rule: TestRule = InstantTaskExecutorRule()

    //region Mocks

    private val uri = mockk<Uri>()
    private val app = mockk<Application>()
    //endregion

    private val vm = MainViewModel(app)

    //region onButtonClicked Tests

    @Test
    fun `When onButtonClicked was called with empty user1Name and empty user2Name, then should navigate with actionMainFragmentToGameFragment direction with null user1Name and user2Name`() {
        //When
        vm.onButtonClicked("", "")

        //Then
        assertEquals(
            ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToGameFragment()),
            vm.viewAction.value
        )
    }

    @Test
    fun `When onButtonClicked was called with not null user1Name, then should navigate with actionMainFragmentToGameFragment direction with not null user1Name`() {

        //When
        vm.onButtonClicked("ibobo", "")

        //Then¬
        assertEquals(
            ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToGameFragment().apply { user1 = "ibobo" }),
            vm.viewAction.value
        )
    }

    @Test
    fun `When onButtonClicked was called with not null user2Name, then should navigate with actionMainFragmentToGameFragment direction with not null user2Name`() {

        //When
        vm.onButtonClicked("", "ibobo")

        //Then
        assertEquals(
            ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToGameFragment().apply { user2 = "ibobo" }),
            vm.viewAction.value
        )
    }

    @Test
    fun `Given firstUserPhotoUpdate was called with uri, when onButtonClicked was called, then should navigate with actionMainFragmentToGameFragment direction with not null user1Photo`() {
        //Given
        vm.firstUserPhotoUpdate(uri)
        //When
        vm.onButtonClicked("", "")

        //Then
        assertEquals(
            ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToGameFragment().apply { user1Photo = uri.toString() }),
            vm.viewAction.value
        )
    }

    @Test
    fun `Given secondUserPhotoUpdate was called with uri, when onButtonClicked was called, then should navigate with actionMainFragmentToGameFragment direction with not null user2Photo`() {
        //Given
        vm.secondUserPhotoUpdate(uri)
        //When
        vm.onButtonClicked("", "")

        //Then
        assertEquals(
            ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToGameFragment().apply { user2Photo = uri.toString() }),
            vm.viewAction.value
        )
    }
    //endregion

    @Test
    fun `When firstUserPhotoUpdate was called with not null userUri, then should navigate with actionMainFragmentToGameFragment direction with not null userUri`() {

        //When
        vm.onButtonClicked("", "ibobo")

        //Then
        assertEquals(
            ViewAction.NavigateWithDirection(MainFragmentDirections.actionMainFragmentToGameFragment().apply { user2 = "ibobo" }),
            vm.viewAction.value
        )
    }


    @Test
    fun`Given userId when onPhotoClicked was called with userId, then selectedUser must be userId`(){
        //Given
        val userId = 123

        //When
        vm.onPhotoClicked(userId)

        //Then
        assertEquals(userId,vm.selectedUser.value)
    }

    @Test
    fun `When firstUserPhotoUpdate with uri then firstUserPhotoUri value should be uri`() {
        //When
        vm.firstUserPhotoUpdate(uri)

        //Then
        assertEquals(uri, vm.firstUserPhotoUri.value)

    }

    @Test
    fun `When secondUserPhotoUpdate with uri then secondUserPhotoUri value should be uri`() {
        //When
        vm.secondUserPhotoUpdate(uri)

        //Then
        assertEquals(uri, vm.secondUserPhotoUri.value)

    }
}