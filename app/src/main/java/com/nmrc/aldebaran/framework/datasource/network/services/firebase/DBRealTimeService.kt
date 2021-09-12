package com.nmrc.aldebaran.framework.datasource.network.services.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.nmrc.aldebaran.business.domain.model.DataSensor
import javax.inject.Singleton

@Singleton
object DBRealTimeService {

    private val database = Firebase.database
    const val TEMPERATURE = "temperature"
    const val HUMIDITY = "humidity"
    const val SUNLIGHT = "sunlight"
    const val TAG = "DEBUG"

    fun writeDataBase(reference: String, value: String) =
        database.getReference(reference).setValue(value)

    fun readDataBase(reference: String, doSomething: (DataSensor) -> Unit) =
        database.getReference(reference).addValueEventListener(object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            val value = snapshot.getValue<Double>()!!
            when(reference) {
                TEMPERATURE -> doSomething(DataSensor.Temperature(value))
                HUMIDITY -> doSomething(DataSensor.Humidity(value))
                SUNLIGHT -> doSomething(DataSensor.Sunlight(value))
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    })
}