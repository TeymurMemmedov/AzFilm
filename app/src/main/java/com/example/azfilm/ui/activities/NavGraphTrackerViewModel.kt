package com.example.azfilm.ui.activities

import androidx.annotation.NavigationRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class NavGraphTrackerViewModel : ViewModel() {
    var navGraphId = MutableLiveData<Int>()

    var navDestionationId = MutableLiveData<Int>()

    fun setNavGraph(@NavigationRes navGraphResId: Int) {
        navGraphId.postValue(navGraphResId)
    }

    fun setNavDest( navDestination: Int) {
        navDestionationId.postValue(navDestination)
    }
}
