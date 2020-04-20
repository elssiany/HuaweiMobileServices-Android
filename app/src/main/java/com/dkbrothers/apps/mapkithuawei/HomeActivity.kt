package com.dkbrothers.apps.mapkithuawei

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.analyticskit.AnalyticsKitActivity
import com.dkbrothers.apps.mapkithuawei.appperformancemanagement.AppPerformanceManagementActivity
import com.dkbrothers.apps.mapkithuawei.crashservice.CrashServiceActivity
import com.dkbrothers.apps.mapkithuawei.locationkit.LocationKitActivity
import com.dkbrothers.apps.mapkithuawei.mapkit.MapKitActivity
import com.dkbrothers.apps.mapkithuawei.pushkit.PuskKitActivity
import com.dkbrothers.apps.mapkithuawei.remoteconfiguration.RemoteConfigurationActivity
import com.huawei.agconnect.crash.AGConnectCrash
import com.huawei.agconnect.remoteconfig.AGConnectConfig


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val remoteConfig = AGConnectConfig.getInstance()
        remoteConfig.setDeveloperMode(true)
        remoteConfig.applyDefault(R.xml.remote_config)
    }

    fun onPushKit(view: View) {
        startActivity(Intent(this, PuskKitActivity::class.java))
    }

    fun onMapKit(view: View) {
        startActivity(Intent(this, MapKitActivity::class.java))
    }

    fun onAnalyticsKit(view: View) {
        startActivity(Intent(this, AnalyticsKitActivity::class.java))
    }

    fun onLocationKit(view: View) {
        startActivity(Intent(this, LocationKitActivity::class.java))
    }

    fun onCrashService(view: View) {
        startActivity(Intent(this, CrashServiceActivity::class.java))
    }


    fun onRemoteConfiguration(view: View) {
        startActivity(Intent(this, RemoteConfigurationActivity::class.java))
    }


    fun onAppPerformanceManagement(view: View) {
        startActivity(Intent(this, AppPerformanceManagementActivity::class.java))
    }

}
