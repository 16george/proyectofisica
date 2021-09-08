package com.nmrc.aldebaran.business.domain.model

data class Ubication(val latitude: Double, val longitude: Double) {

    fun isNull(): Boolean = latitude == (0).toDouble() || longitude == (0).toDouble()
}