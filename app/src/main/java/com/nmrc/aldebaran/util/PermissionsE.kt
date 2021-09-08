package com.nmrc.aldebaran.util


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat

typealias all = Manifest.permission

object PermissionsE {

    private const val GRANTED = PackageManager.PERMISSION_GRANTED

    object Location {

        private const val ID_PERMISSION = 12345

        fun checkPermissions(activity: Activity, granted: () -> Unit, denied: () -> Unit) {
            if (check(
                    activity.baseContext,
                    arrayOf(all.ACCESS_COARSE_LOCATION, all.ACCESS_FINE_LOCATION)
                ) &&
                isLocationEnable(activity)
            )
                granted()
            else {
                denied()
                requestPermissions(activity)
            }
        }


        private fun requestPermissions(activity: Activity) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(all.ACCESS_COARSE_LOCATION, all.ACCESS_FINE_LOCATION),
                ID_PERMISSION
            )
        }

        private fun isLocationEnable(activity: Activity): Boolean {
            return (activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager).run {
                isProviderEnabled(LocationManager.GPS_PROVIDER) || isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            }
        }
    }

    private fun check(context: Context, permission: Array<String>): Boolean {
        var state = false
        permission.forEach { p ->
            if (ActivityCompat.checkSelfPermission(context, p) == GRANTED)
                state = true
        }

        return state
    }
}