package com.nmrc.aldebaran.framework.datasource.network.services

import android.annotation.SuppressLint
import android.app.Activity
import com.google.android.gms.location.LocationServices

object LocationGET {

    @SuppressLint("MissingPermission")
    fun invoke(activity: Activity, doSomething: (latitude: Double, longitude: Double) -> Unit) {
        LocationServices.getFusedLocationProviderClient(activity).apply {
            lastLocation.addOnCompleteListener { task ->
                task.result?.let { location ->
                    doSomething(location.latitude, location.longitude)
                }
            }
        }
    }

}