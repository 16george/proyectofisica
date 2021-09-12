package com.nmrc.aldebaran.business.data.implementation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.nmrc.aldebaran.business.domain.model.DataSensor
import kotlinx.coroutines.delay

class DataRealTimeGraphVM : ViewModel() {

    companion object {
        const val TEMPERATURE = "TEMPERATURE"
        const val HUMIDITY = "HUMIDITY"
        const val SUNLIGHT = "SUNLIGHT"
        const val TIME_DELAY = 1000L
    }


    /**
     *
     * TEMPERATURE
     *
     * */
    private val temperature = mutableListOf<Entry>()
    private val _lineTemperatureDataSet = MutableLiveData(LineDataSet(temperature, TEMPERATURE))
    val lineTemperatureDataSet: LiveData<LineDataSet> = this._lineTemperatureDataSet


    /**
     *
     * HUMIDITY
     *
     * */
    private val humidity = mutableListOf<Entry>()
    private val _lineHumidityDataSet = MutableLiveData(LineDataSet(humidity, HUMIDITY))
    val lineHumidityDataSet: LiveData<LineDataSet> get() = this._lineHumidityDataSet

    /**
     *
     * SUNLIGHT
     *
     * */
    private val sunlight = mutableListOf<Entry>()
    private val _lineSunlightDataSet = MutableLiveData(LineDataSet(sunlight, SUNLIGHT))
    val lineSunlightDataSet: LiveData<LineDataSet> get() = this._lineSunlightDataSet

    /**
     * REAL TIME DATA SET
     */

    suspend fun addTemperature(valueTemperature: DataSensor.Temperature) {
        var aux = 0f
        while (true) {
            temperature.add(Entry(aux, valueTemperature.value.toFloat()))
            aux++
            this._lineTemperatureDataSet.value = LineDataSet(temperature, TEMPERATURE)
            delay(TIME_DELAY)
        }
    }

    suspend fun addHumidity(valueHumidity: DataSensor.Humidity) {
        var aux = 0f
        while (true) {
            humidity.add(Entry(aux, valueHumidity.value.toFloat()))
            aux++
            this._lineHumidityDataSet.value = LineDataSet(humidity, HUMIDITY)
            delay(TIME_DELAY)
        }
    }

    suspend fun addSunlight(valueSunlight: DataSensor.Sunlight) {
        var aux = 0f
        while (true) {
            sunlight.add(Entry(aux, valueSunlight.value.toFloat()))
            aux++
            this._lineSunlightDataSet.value = LineDataSet(sunlight, SUNLIGHT)
            delay(TIME_DELAY)
        }
    }

    init {
        temperature.add(Entry(0f, 5f))
        temperature.add(Entry(1f, 15f))
        temperature.add(Entry(2f, 3f))
        this._lineTemperatureDataSet.value = LineDataSet(temperature, TEMPERATURE)


        humidity.add(Entry(0f, 5f))
        humidity.add(Entry(1f, 56f))
        humidity.add(Entry(2f, 82f))
        this._lineHumidityDataSet.value = LineDataSet(humidity, HUMIDITY)

        sunlight.add(Entry(0f, 20f))
        sunlight.add(Entry(1f, 15f))
        sunlight.add(Entry(2f, 12f))
        this._lineSunlightDataSet.value = LineDataSet(sunlight, SUNLIGHT)
    }
}