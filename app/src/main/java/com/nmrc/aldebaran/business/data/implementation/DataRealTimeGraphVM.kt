package com.nmrc.aldebaran.business.data.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.nmrc.aldebaran.business.domain.model.DataSensor

class DataRealTimeGraphVM : ViewModel() {

    companion object {
        const val TEMPERATURE = "TEMPERATURE"
        const val HUMIDITY = "HUMIDITY"
        const val SUNLIGHT = "SUNLIGHT"
    }


    /**
     *
     * TEMPERATURE
     *
     * */
    private val _temperatureGD = MutableLiveData<DataSensor.Temperature>()
    val temperatureGD: LiveData<DataSensor.Temperature> get() = _temperatureGD
    private val temperature = mutableListOf<Entry>()
    private val _lineTemperatureDataSet = MutableLiveData(LineDataSet(temperature, TEMPERATURE))
    val lineTemperatureDataSet: LiveData<LineDataSet> = this._lineTemperatureDataSet


    /**
     *
     * HUMIDITY
     *
     * */
    private val _humidityGD = MutableLiveData<DataSensor.Humidity>()
    val humidityGD: LiveData<DataSensor.Humidity> get() = _humidityGD
    private val humidity = mutableListOf<Entry>()
    private val _lineHumidityDataSet = MutableLiveData(LineDataSet(humidity, HUMIDITY))
    val lineHumidityDataSet: LiveData<LineDataSet> get() = this._lineHumidityDataSet

    /**
     *
     * SUNLIGHT
     *
     * */
    private val _sunlightGD = MutableLiveData<DataSensor.Sunlight>()
    val sunLightGD: LiveData<DataSensor.Sunlight> get() = _sunlightGD
    private val sunlight = mutableListOf<Entry>()
    private val _lineSunlightDataSet = MutableLiveData(LineDataSet(sunlight, SUNLIGHT))
    val lineSunlightDataSet: LiveData<LineDataSet> get() = this._lineSunlightDataSet

    /**
     * REAL TIME DATA SET
     */

    fun addTemperature(valueTemperature: DataSensor.Temperature) {
        temperature.add(Entry(0f, valueTemperature.value.toFloat()))
        this._lineTemperatureDataSet.value = LineDataSet(temperature, TEMPERATURE)
        _temperatureGD.value = valueTemperature
    }


    fun addHumidity(valueHumidity: DataSensor.Humidity) {
        humidity.add(Entry(0f, valueHumidity.value.toFloat()))
        this._lineHumidityDataSet.value = LineDataSet(humidity, HUMIDITY)
        _humidityGD.value = valueHumidity
    }


    fun addSunlight(valueSunlight: DataSensor.Sunlight) {
        sunlight.add(Entry(0f, valueSunlight.value.toFloat()))
        this._lineSunlightDataSet.value = LineDataSet(sunlight, SUNLIGHT)
        _sunlightGD.value = valueSunlight
    }

    init {

        for(i in (0..100)) {
            temperature.add(Entry(i.toFloat(), (12..25).random().toFloat()))
        }
        this._lineTemperatureDataSet.value = LineDataSet(temperature, TEMPERATURE)


        for(i in (0..100)) {
            humidity.add(Entry(i.toFloat(), (20..100).random().toFloat()))
        }
        this._lineHumidityDataSet.value = LineDataSet(humidity, HUMIDITY)

        for(i in (0..100)) {
            sunlight.add(Entry(i.toFloat(), (10..200).random().toFloat()))
        }
        this._lineSunlightDataSet.value = LineDataSet(sunlight, SUNLIGHT)
    }
}