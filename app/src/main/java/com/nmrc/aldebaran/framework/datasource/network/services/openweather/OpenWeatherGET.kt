package com.nmrc.aldebaran.framework.datasource.network.services.openweather

import com.google.android.gms.maps.model.TileProvider
import com.google.android.gms.maps.model.UrlTileProvider
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather
import com.nmrc.aldebaran.BuildConfig
import com.nmrc.aldebaran.business.domain.model.Ubication
import kotlinx.coroutines.coroutineScope
import java.net.MalformedURLException
import java.net.URL
import java.time.Instant
import java.time.format.DateTimeFormatter


object OpenWeatherGET {

    private const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY

    object Layer : UrlTileProvider(256, 256) {
        private val tileProvider: TileProvider = this

        private var LAYER = Layers.TEMPERATURE

        object Layers{
            const val TEMPERATURE = "temp_new"
            const val PRECIPITATIONS = "precipitation_new"
            const val CLOUDS = "clouds_new"
        }

        fun getTileProvider(layer: String): TileProvider {
            LAYER = layer
            return tileProvider
        }

        override fun getTileUrl(x: Int, y: Int, z: Int): URL? {
            val call = "https://tile.openweathermap.org/map/$LAYER/${z}/${x}/${y}.png?appid=$API_KEY"
            return try {
                URL(call)
            } catch (e: MalformedURLException) {
                throw AssertionError(e)
            }
        }
    }

    object Helper : CurrentWeatherCallback {

        var maxTemperature: String = ""
        var minTemperature: String = ""
        var humidity: String = ""
        var windSpeed: String = ""
        var windDirection: String = ""
        var visibility: String = ""
        var sunrise: String = ""
        var sunset: String = ""


        suspend fun invoke(ubication: Ubication,
                           language: String,
                           unit: String) {

            coroutineScope {
                OpenWeatherMapHelper(API_KEY).run {
                    setUnits(unit)
                    setLanguage(language)
                    getCurrentWeatherByGeoCoordinates(ubication.latitude, ubication.longitude, this@Helper)
                }
            }
        }

        override fun onSuccess(currentWeather: CurrentWeather) {
            with(currentWeather) {
                maxTemperature = "Max Temperature ${main.tempMax} °C"
                minTemperature = "Min Temperature ${main.tempMin} °C"
                humidity = "Humidity ${main.humidity} %"
                windSpeed = "Wind Speed ${wind.speed} m/s"
                windDirection = "Wind Direction ${wind.deg}°"
                this@Helper.visibility = "Visibility  $visibility m"
                sunrise = "Sunrise " + DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(this.sys.sunrise))
                sunset = "Sunset " + DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(this.sys.sunset))
            }
        }

        override fun onFailure(t: Throwable?) {
            t?.printStackTrace()
        }
    }
}