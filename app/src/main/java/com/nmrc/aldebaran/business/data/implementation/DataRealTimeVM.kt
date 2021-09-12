package com.nmrc.aldebaran.business.data.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nmrc.aldebaran.business.domain.model.DataSensor

class DataRealTimeVM : ViewModel() {

    private val _temperature = MutableLiveData<DataSensor.Temperature>()
    val temperature: LiveData<DataSensor.Temperature> get() = _temperature
    private val _humidity = MutableLiveData<DataSensor.Humidity>()
    val humidity: LiveData<DataSensor.Humidity> get() = _humidity
    private val _sunlight = MutableLiveData<DataSensor.Sunlight>()
    val sunlight: LiveData<DataSensor.Sunlight> get() = _sunlight


    init {
        _temperature.value = DataSensor.Temperature(0.0)
        _humidity.value = DataSensor.Humidity(0.0)
        _sunlight.value = DataSensor.Sunlight(0.0)
    }

    fun addTemperature(valueTemperature: DataSensor.Temperature) {
        _temperature.value = valueTemperature
    }

    fun addHumidity(valueHumidity: DataSensor.Humidity) {
        _humidity.value = valueHumidity
    }

    fun addSunLight(valueSunLight: DataSensor.Sunlight) {
        _sunlight.value = valueSunLight
    }
}