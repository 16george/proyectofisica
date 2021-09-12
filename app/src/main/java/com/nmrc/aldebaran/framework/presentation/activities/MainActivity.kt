package com.nmrc.aldebaran.framework.presentation.activities

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.nmrc.aldebaran.R
import com.nmrc.aldebaran.business.data.implementation.DataRealTimeGraphVM
import com.nmrc.aldebaran.business.data.implementation.UbicationVM
import com.nmrc.aldebaran.business.domain.model.DataSensor
import com.nmrc.aldebaran.databinding.ActivityMainBinding
import com.nmrc.aldebaran.framework.datasource.network.services.firebase.DBRealTimeService
import com.nmrc.aldebaran.framework.datasource.network.services.googlemaps.LocationGET
import com.nmrc.aldebaran.util.PermissionsE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val navController by lazy {
        Navigation.findNavController(
            this,
            R.id.fragmentMainContent
        )
    }
    private val vm: UbicationVM by viewModels()
    private val vmData: DataRealTimeGraphVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        confNav()

        /**
         * GPS LOCATION
         */

        PermissionsE.Location.checkPermissions(this, {

            lifecycleScope.launch(Dispatchers.IO) {
                LocationGET.invoke(this@MainActivity) { latitude, longitude ->
                    vm.setCoordinates(latitude, longitude)
                }
            }
        }, {
            Toast.makeText(this, "PERMISO DENEGADO", Toast.LENGTH_LONG).show()
        })

        /**
         * GET DATA FROM REALTIME DATABASE
         */

        requesterSunLight()
        requesterHumidity()
        requesterTemperature()
    }

    private fun confNav() {
        NavigationUI.setupWithNavController(binding.bnvMainNavigation, navController)
    }
    private fun requesterSunLight() {
        lifecycleScope.launch(Dispatchers.IO) {
            DBRealTimeService.readDataBase(DBRealTimeService.SUNLIGHT) { sunLight ->
                vmData.addSunlight(sunLight as DataSensor.Sunlight)

            }
        }
    }

    private fun requesterHumidity() {
        lifecycleScope.launch(Dispatchers.IO) {
            DBRealTimeService.readDataBase(DBRealTimeService.HUMIDITY) { humidity ->
                vmData.addHumidity(humidity as DataSensor.Humidity)
            }
        }
    }

    private fun requesterTemperature() {
        lifecycleScope.launch(Dispatchers.IO) {
            DBRealTimeService.readDataBase(DBRealTimeService.TEMPERATURE) { temperature ->
                vmData.addTemperature(temperature as DataSensor.Temperature)
            }
        }
    }
}