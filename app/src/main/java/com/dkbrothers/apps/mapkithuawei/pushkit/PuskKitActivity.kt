package com.dkbrothers.apps.mapkithuawei.pushkit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessaging


/*
 * todo Push Kit Docs
  *https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/push-introduction
  * Codelab:
  * https://developer.huawei.com/consumer/en/codelab/HMSPushKit/index.html#0
 * */
class PuskKitActivity : AppCompatActivity() {

    private val TAG = "PushDemoLog"

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
            Uri.parse(getString(R.string.push_kit_documentation_link))))
    }

    fun onGetToken(view: View) {
        Toast.makeText(applicationContext,getString(R.string.gettingtoken_please_wait),
            Toast.LENGTH_LONG).show()
        getToken()
        tvToken?.text = getString(R.string.please_wait)
    }

    fun onSubscribeToOffers(view: View) {
        Toast.makeText(applicationContext,getString(R.string.subscribing_offers_please_wait),
            Toast.LENGTH_LONG).show()
        subscribeToATopic()
    }


    private fun showLog(log: String) {
        runOnUiThread {
            tvToken?.text = log
        }
    }


    private fun subscribeToATopic(){
        try {
            HmsMessaging.getInstance(this).subscribe("summer-offers")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "subscribe Complete")
                        Toast.makeText(applicationContext,getString(R.string.subscribe_complete),
                            Toast.LENGTH_LONG).show()
                    } else {
                        Log.e(
                            TAG,
                            "subscribe failed: ret=" + task.exception.message)
                        Toast.makeText(applicationContext,"Subscribe failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "subscribe failed: exception=" + e.message)
            Toast.makeText(applicationContext,getString(R.string.subscribe_failed),
                Toast.LENGTH_LONG).show()
        }
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
                        showLog(pushtoken)
                    }else{
                        showLog(getString(R.string.pushkit_message_not_token))
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "getToken failed, $e")
                }
            }
        }.start()
    }



}
