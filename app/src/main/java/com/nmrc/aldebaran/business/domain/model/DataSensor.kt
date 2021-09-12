package com.nmrc.aldebaran.business.domain.model

sealed class DataSensor(open val value: Double? = 0.0) {

    /**         DATA        **/

    abstract val maxValue: Double

    /**
     *
     * TEMPERATURE
     *
     * MEASUREMENT UNITS = °C
     *
     * */
    data class Temperature(override val value: Double) : DataSensor(value) {
        override val maxValue: Double
            get() = 60.0 // Celsius
    }

    /**
     *
     * HUMIDITY
     *
     * MEASUREMENT UNITS = g/m^3 -> %
     *
     * */
    data class Humidity(override val value: Double) : DataSensor(value) {
        override val maxValue: Double
            get() = 100.0  // Percentage
    }

    /**
     *
     * SUNLIGHT
     *
     * MEASUREMENT UNITS = LUX
     *
     * */
    data class Sunlight(override val value: Double) : DataSensor(value) {
        override val maxValue: Double
            get() = 10.0 // Lux
    }
}
