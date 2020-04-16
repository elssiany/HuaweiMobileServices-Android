package com.dkbrothers.apps.mapkithuawei.mapkit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dkbrothers.apps.mapkithuawei.R
import com.dkbrothers.apps.mapkithuawei.numRandom
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions


/*
 * todo Map Kit Docs
  *https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/hms-map-v4-abouttheservice
  * Codelab:
  * https://developer.huawei.com/consumer/en/codelab/HMSMapKit/index.html#0
 * */
class MapKitActivity : AppCompatActivity(), OnMapReadyCallback {


    //Huawei map
    private var hMap: HuaweiMap? = null

    private var mMapView: MapView? = null

    private val PERMISSIONS_REQUEST_MAPS = 9897

    private val RUNTIME_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val locations = arrayOf(
        LocationStoreModel(
            "Centro Comercial", R.drawable.smart_city,
            4.721864399999999, -74.0918624
        ),
        LocationStoreModel(
            "Claro Store", R.drawable.marker,
            4.75055564, -74.0952778
        ),
        LocationStoreModel(
            "Bancolombia", R.drawable.cryptocurrency,
            4.6988167, -74.0409894
        ),
        LocationStoreModel(
            "Centro Comercial", R.drawable.smart_city,
            4.721864399999999, -74.0918624
        )
    )

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_kit)
        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?){

        if (!hasPermissions(this, RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, PERMISSIONS_REQUEST_MAPS)
        }

        //get mapview instance
        mMapView = findViewById(R.id.mapView)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView?.onCreate(mapViewBundle)
        //get map instance
        mMapView?.getMapAsync(this)
    }




    override fun onMapReady(huaweiMap: HuaweiMap?) {
        //get map instance in a callback method
        hMap = huaweiMap
        hMap?.isMyLocationEnabled = true// Enable the my-location overlay.
        hMap?.uiSettings?.isMyLocationButtonEnabled = true// Enable the my-location icon.
        /*
        hMap?.setOnMapClickListener { latLng ->
            Toast.makeText(
                applicationContext,
                "onMapClick:$latLng",
                Toast.LENGTH_SHORT
            ).show()
        }
        */
        hMap?.setOnMarkerClickListener { marker ->
            Toast.makeText(
                applicationContext,
                "onMarkerClick:" + marker.title,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
        Toast.makeText(applicationContext, "Mapa cargado", Toast.LENGTH_SHORT).show()
    }


    private var mMarker: Marker? = null

    fun addMarker(view: View?) {
        val location = locations[numRandom(0,locations.size-1)]
        val latLng = LatLng(location.latitud,location.longitud)
        if (null != mMarker) {
            //para eliminar el marker anterior
            //mMarker!!.remove()
        }
        val options = MarkerOptions()
            .position(LatLng(location.latitud, location.longitud))
            .icon(BitmapDescriptorFactory.fromResource(location.iconId))
            .title(location.nombre)
            .snippet("This is a snippet example!")
        mMarker = hMap?.addMarker(options)
        hMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
        //Method for setting custom information window.
        hMap?.setInfoWindowAdapter(CustomInfoWindowMapAdapter(this,location))
    }


    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSIONS_REQUEST_MAPS){
            if(grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permiso concedido", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Permiso denegado", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }


}
