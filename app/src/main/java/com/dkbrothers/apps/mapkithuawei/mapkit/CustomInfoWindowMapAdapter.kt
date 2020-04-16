package com.dkbrothers.apps.mapkithuawei.mapkit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.Marker

class CustomInfoWindowMapAdapter(private val mContext: Context,
                                 private val locationModel:LocationStoreModel) : HuaweiMap.InfoWindowAdapter {

    private var mWindow:View =
        LayoutInflater.from(mContext).inflate(R.layout.item_marker_point_in_map,null)

    private fun rendowWindow(marker: Marker, view:View){
        val tvTitle= view.findViewById<TextView>(R.id.headquarter_name)
        val tvAddress= view.findViewById<TextView>(R.id.headquarter_address)
        val tvPhone= view.findViewById<TextView>(R.id.opening_hours)
        tvTitle.text = locationModel.nombre
        tvAddress.text = mContext.getString(R.string.location_lat_long,
            locationModel.latitud.toString(),locationModel.longitud.toString())
        tvPhone.text = String.format("Phone: \n%s","+57 5968946")

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindow(marker,mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View {
        rendowWindow(marker,mWindow)
        return mWindow
    }

}