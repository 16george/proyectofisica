package com.nmrc.aldebaran.business.data.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nmrc.aldebaran.business.domain.model.Ubication

class UbicationVM : ViewModel() {

    private var ubication = MutableLiveData<Ubication>()

    init {
        ubication.value = Ubication((0).toDouble(), (0).toDouble())
    }

    fun setCoordinates(latitude: Double, longitude: Double) {
        ubication.value = Ubication(latitude, longitude)
    }

    fun getLastUbication(): Ubication = ubication.value!!
}