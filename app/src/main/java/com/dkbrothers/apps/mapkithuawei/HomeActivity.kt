package com.dkbrothers.apps.mapkithuawei

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.locationkit.LocationKitActivity
import com.dkbrothers.apps.mapkithuawei.mapkit.MapKitActivity
import com.dkbrothers.apps.mapkithuawei.pushkit.PuskKitActivity


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onPushKit(view: View) {
        startActivity(Intent(this, PuskKitActivity::class.java))
    }

    fun onMapKit(view: View) {
        startActivity(Intent(this, MapKitActivity::class.java))
    }


    fun onLocationKit(view: View) {
        startActivity(Intent(this, LocationKitActivity::class.java))
    }


}
