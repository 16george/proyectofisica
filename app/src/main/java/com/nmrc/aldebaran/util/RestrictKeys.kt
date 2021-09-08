package com.nmrc.aldebaran.util

import android.app.Application
import com.google.android.libraries.places.api.Places


object RestrictKeys : Application() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(this, com.nmrc.aldebaran.BuildConfig.GOOGLE_MAPS_API_KEY)
        Places.initialize(this, com.nmrc.aldebaran.BuildConfig.OPEN_WEATHER_API_KEY)
    }
}