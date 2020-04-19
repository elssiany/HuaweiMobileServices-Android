package com.dkbrothers.apps.mapkithuawei.appperformancemanagement

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.agconnect.apms.APMS

/*
 * todo App Performance Management Docs
  *https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-apms-introduction
 * */
class AppPerformanceManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_performance_management)
    }

    fun onEnable(view: View) {
        // you will enable APM
        APMS.getInstance().enableCollection(true)
        Toast.makeText(applicationContext,R.string.enable,Toast.LENGTH_SHORT).show()
    }
    fun onDisable(view: View) {
        // you will disable APM
        APMS.getInstance().enableCollection(false)
        Toast.makeText(applicationContext,R.string.disable,Toast.LENGTH_SHORT).show()
    }

    fun onGoDocumentation(view: View) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.apm_documentation_link)))
        )
    }
}
