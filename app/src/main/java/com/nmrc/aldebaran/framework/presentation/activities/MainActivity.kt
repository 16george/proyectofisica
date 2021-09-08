package com.nmrc.aldebaran.framework.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.nmrc.aldebaran.R
import com.nmrc.aldebaran.databinding.ActivityMainBinding
import com.nmrc.aldebaran.framework.datasource.network.services.LocationGET
import com.nmrc.aldebaran.util.PermissionsE
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val navController by lazy { Navigation.findNavController(this, R.id.fragmentMainContent) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        confNav()

        // GPS LOCATION

        PermissionsE.Location.checkPermissions(this, {

            lifecycleScope.launch {
                LocationGET.invoke(this@MainActivity) {latitude, longitude ->

                    Toast.makeText(this@MainActivity, "COORDENADAS: LATITUD: $latitude, LONGITUD: $longitude", Toast.LENGTH_LONG).show()
                }
            }
        },{
            Toast.makeText(this, "PERMISO DENEGADO", Toast.LENGTH_LONG).show()
        })
    }

    private fun confNav() {
        NavigationUI.setupWithNavController(binding.bnvMainNavigation, navController)
    }
}