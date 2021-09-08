package com.nmrc.aldebaran.framework.datasource.network.services.googlemaps

import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.nmrc.aldebaran.R
import com.nmrc.aldebaran.business.domain.model.Ubication
import com.nmrc.aldebaran.framework.datasource.network.services.openweather.OpenWeatherGET

object GoogleMapsGET : OnMapReadyCallback {

    private const val ZOOM = 12f
    private lateinit var ubication: Ubication
    private lateinit var selectLayer: String

    operator fun invoke(fragment: Fragment, ubication: Ubication, layer: String) {
        GoogleMapsGET.ubication = ubication
        selectLayer = layer
        (fragment.childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment).apply {
            getMapAsync(this@GoogleMapsGET)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        val centerMap = LatLng(ubication.latitude, ubication.longitude)

        map.apply {
            animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, ZOOM))
            addTileOverlay(
                TileOverlayOptions().tileProvider(
                    OpenWeatherGET.Layer.getTileProvider(
                        selectLayer
                    )
                )
            )
            mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
    }
}