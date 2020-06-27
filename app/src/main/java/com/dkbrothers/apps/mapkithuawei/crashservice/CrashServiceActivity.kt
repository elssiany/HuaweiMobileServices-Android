package com.dkbrothers.apps.mapkithuawei.crashservice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.agconnect.crash.AGConnectCrash

/*
 * todo Crash Service Docs
  *https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-crash-introduction
  * Codelab
  * https://developer.huawei.com/consumer/en/codelab/CrashService/index.html#0
 * */
class CrashServiceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_service)
    }


    fun onCrashTest(view: View) {
        AGConnectCrash.getInstance().testIt(this)
    }


    fun onGoDocumentation(view: View) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.push_kit_documentation_link)))
        )
    }

}
