package com.nmrc.aldebaran.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.data.LineData
import com.nmrc.aldebaran.R
import com.nmrc.aldebaran.business.data.implementation.DataRealTimeGraphVM
import com.nmrc.aldebaran.business.data.implementation.LinearSetup
import com.nmrc.aldebaran.databinding.FragmentDataBinding

class DataFragment : Fragment(R.layout.fragment_data) {

    private lateinit var chartTemperature: LinearSetup
    private lateinit var chartHumidity: LinearSetup
    private lateinit var chartSunLight: LinearSetup
    private val vm: DataRealTimeGraphVM by activityViewModels()
    private val binding: FragmentDataBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        /** TEMPERATURE REAL TIME **/
        observableTemperature()

        /** HUMIDITY REAL TIME **/
        observableHumidity()

        /** SUNLIGHT REAL TIME **/
        observableSunLight()

    }

    private fun observableSunLight() {
        vm.lineSunlightDataSet.observe(viewLifecycleOwner) { lineDataSet ->
            chartSunLight.styleLineDataSet(lineDataSet)
            with(binding.graphSunlight) {
                data = LineData(lineDataSet)
                this.invalidate()
            }
        }
    }

    private fun observableHumidity() {
        vm.lineHumidityDataSet.observe(viewLifecycleOwner) { lineDataSet ->
            chartHumidity.styleLineDataSet(lineDataSet)
            with(binding.graphHumidity) {
                data = LineData(lineDataSet)
                this.invalidate()
            }
        }
    }

    private fun observableTemperature() {
        vm.lineTemperatureDataSet.observe(viewLifecycleOwner) { lineDataSet ->
            chartTemperature.styleLineDataSet(lineDataSet)
            with(binding.graphTemperature) {
                data = LineData(lineDataSet)
                this.invalidate()
            }
        }
    }

    private fun setUp() {
        chartTemperature = LinearSetup(requireContext(), R.drawable.shape_bg_linear_temperature)
        chartTemperature.styleChart(binding.graphTemperature)

        chartHumidity = LinearSetup(requireContext(), R.drawable.shape_bg_linear_humidity)
        chartHumidity.styleChart(binding.graphHumidity)

        chartSunLight = LinearSetup(requireContext(), R.drawable.shape_bg_linear_sunlight)
        chartSunLight.styleChart(binding.graphSunlight)
    }

}