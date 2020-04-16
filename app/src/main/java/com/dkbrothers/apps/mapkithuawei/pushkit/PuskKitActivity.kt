package com.dkbrothers.apps.mapkithuawei.pushkit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId


class PuskKitActivity : AppCompatActivity() {

    private val TAG = "PuskKitActivity"

    private var tvToken:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pusk_kit)
        initViews()
    }


    private fun initViews(){
        tvToken = findViewById(R.id.tv_token)
    }

    fun onGoDocumentation(view: View) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
            Uri.parse("https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/push-introduction"))
        )
    }

    /**
     * Obtain a token.
     * todo If the EMUI version is 10.0 or later on a Huawei device, a token is returned through
      * the HmsInstanceId.getInstance.getToken method described in the step of applying for a token
     */
    private fun getToken() {
        // get token
        object : Thread() {
            override fun run() {
                try {
                    val appId =
                        AGConnectServicesConfig.fromContext(this@PuskKitActivity)
                            .getString("client/app_id")
                    val pushtoken = HmsInstanceId.getInstance(this@PuskKitActivity).getToken(appId, "HCM")
                    if (!TextUtils.isEmpty(pushtoken)) {
                        Log.i(TAG, "get token:$pushtoken")
                        tvToken?.text = pushtoken
                    }else{
                        tvToken?.text = getString(R.string.pushkit_message_not_token)
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "getToken failed, $e")
                }
            }
        }.start()
    }

    fun onGetToken(view: View) {
        getToken()
    }

}
