package com.leeseungyun1020.position

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsViewModel : ViewModel() {
    val location = MutableLiveData<Location>()

    fun updateLocation(updatedLocation: Location) {
        location.value = updatedLocation
    }
}