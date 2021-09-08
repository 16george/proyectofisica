package com.nmrc.aldebaran.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kwabenaberko.openweathermaplib.constant.Languages
import com.kwabenaberko.openweathermaplib.constant.Units
import com.nmrc.aldebaran.R
import com.nmrc.aldebaran.business.data.implementation.UbicationVM
import com.nmrc.aldebaran.business.domain.model.Ubication
import com.nmrc.aldebaran.databinding.FragmentTodayBinding
import com.nmrc.aldebaran.framework.datasource.network.services.googlemaps.GoogleMapsGET
import com.nmrc.aldebaran.framework.datasource.network.services.openweather.OpenWeatherGET
import com.nmrc.aldebaran.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TodayFragment : Fragment(R.layout.fragment_today) {

    private val vm: UbicationVM by activityViewModels()
    private val binding: FragmentTodayBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMap(10)
        onLayerListener()

    }

    private suspend fun requesterOWM(ubication: Ubication) {
        OpenWeatherGET.Helper.invoke(ubication,Languages.SPANISH,Units.METRIC)

        with(binding) {
            with(OpenWeatherGET.Helper) {
                tvAuxHumidity.text =humidity
                tvAuxMaxTemp.text = maxTemperature
                tvAuxMinTemp.text = minTemperature
                tvAuxWindSpeed.text = windSpeed
                tvAuxWindDirection.text = windDirection
                tvAuxVisibility.text = visibility
                tvAuxSunrise.text = sunrise
                tvAuxSunset.text = sunset
            }
        }
    }

    private fun onLayerListener() {
        binding.cgLayers.setOnCheckedChangeListener { _, checkedId ->
            loadMap(checkedId)
        }
    }

    private fun getLayer(ubication: Ubication, mode: Int) {
        when (mode) {
            R.id.ci_temperature -> GoogleMapsGET(
                this@TodayFragment,
                ubication,
                OpenWeatherGET.Layer.Layers.TEMPERATURE
            )

            R.id.ci_precipitations -> GoogleMapsGET(
                this@TodayFragment,
                ubication,
                OpenWeatherGET.Layer.Layers.PRECIPITATIONS
            )

            R.id.ci_clouds -> GoogleMapsGET(
                this@TodayFragment,
                ubication,
                OpenWeatherGET.Layer.Layers.CLOUDS
            )
            else -> GoogleMapsGET(
                this@TodayFragment,
                ubication,
                OpenWeatherGET.Layer.Layers.TEMPERATURE
            )
        }
    }


    private fun loadMap(mode: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            while (true) {
                val ubication = vm.getLastUbication()
                if (!ubication.isNull()) {
                    getLayer(ubication, mode)
                    requesterOWM(ubication)
                    break
                }
                toast("Getting last location")
                delay(1016)
            }
        }
    }
}