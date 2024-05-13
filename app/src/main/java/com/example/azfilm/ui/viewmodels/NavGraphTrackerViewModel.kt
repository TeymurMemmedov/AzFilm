package com.example.azfilm.ui.viewmodels

import androidx.annotation.NavigationRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class NavGraphTrackerViewModel : ViewModel() {
    var navGraphId = MutableLiveData<Int>()

    fun setNavGraph(@NavigationRes navGraphResId: Int) {
        navGraphId.postValue(navGraphResId)
    }
}
