package com.dkbrothers.apps.mapkithuawei.locationkit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.hmf.tasks.OnFailureListener
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*


class LocationKitActivity : AppCompatActivity() {



    private val TAG = "LocationKitActivity"

    private val PERMISSIONS_REQUEST_LOCATION = 9097
    private var fusedLocationProviderClient:FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var settingsClient: SettingsClient? = null
    private var locationManager:LocationManager? = null
    private var tvLocationActual:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_kit)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        initViews()
        //Verifico los permisos antes de iniciar los settings
        if(checkfPermission()) {
            //Se inicia los settings para obtener ubicacion
            initSettings()
        }
    }


    private fun initViews(){
        tvLocationActual = findViewById(R.id.tv_location_actual)
    }


    fun onGoDocumentation(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.location_kit_documentation_link))))
    }

    private fun initSettings(){

        //todo Se crea un cliente de proveedor de ubicación y cliente de configuración de dispositivo
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)

        //todo Solicito información de ubicación
        mLocationRequest = LocationRequest()
        // set the interval for location updates, in milliseconds.
        //mLocationRequest.interval = 10000
        // set the priority of the request
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        requestLocationUpdatesWithCallback()

    }


    private fun isGPSEnabled():Boolean{
        val providers = locationManager?.allProviders ?: return false
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)?:false
                && providers.contains(LocationManager.GPS_PROVIDER)
    }



    private fun checkfPermission():Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                ActivityCompat.requestPermissions(this, strings, PERMISSIONS_REQUEST_LOCATION)
                return false
            }
        }
        return true
    }


    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locations = locationResult.locations
            if (locations.isNotEmpty()) {
                val location = locations[0]
                tvLocationActual?.text = getString(R.string.location_lat_long,
                    location.longitude,location.latitude)
            }
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            if(!locationAvailability.isLocationAvailable) {
                Toast.makeText(applicationContext,"Ubicación no disponible, por favor verificar GPS",
                    Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun requestLocationUpdatesWithCallback() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()
        // check devices settings before request location updates.
        settingsClient?.checkLocationSettings(locationSettingsRequest)
            ?.addOnSuccessListener {
                Log.i(TAG, "check location settings success")
                // request location updates
                fusedLocationProviderClient
                    ?.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback,
                        Looper.getMainLooper()
                    )
                    ?.addOnSuccessListener {
                        Log.i(TAG, "requestLocationUpdatesWithCallback onSuccess")
                    }
                    ?.addOnFailureListener { e ->
                        Log.e(TAG, "requestLocationUpdatesWithCallback onFailure:" + e.message)
                    }
            }
            ?.addOnFailureListener { e ->
                Log.e(TAG, "checkLocationSetting onFailure:" + e.message)
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(this@LocationKitActivity, 0)
                    } catch (sie: SendIntentException) {
                        Log.e(TAG, "PendingIntent unable to execute request.")
                    }
                }
            }
    }


    /**
     * remove the request with callback
     */
    private fun removeLocationUpdatesWithCallback() {
        fusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
            ?.addOnSuccessListener {
                Log.i(
                    TAG,
                    "removeLocationUpdatesWithCallback onSuccess"
                )
            }
            ?.addOnFailureListener { e ->
                Log.e(
                    TAG,
                    "removeLocationUpdatesWithCallback onFailure:" + e.message
                )
            }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.size > 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                //Se inicia los settings para obtener ubicacion
                initSettings()
                Toast.makeText(this,"Permiso concedido", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Permiso denegado", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        removeLocationUpdatesWithCallback()
        super.onDestroy()
    }

}
