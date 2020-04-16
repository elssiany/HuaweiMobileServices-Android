package com.dkbrothers.apps.mapkithuawei.pushkit

import android.util.Log
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage


class PushKitService: HmsMessageService() {

    private val TAG = "PushKitService"

    /**
    * todo If the EMUI version is earlier than 10.0 on a Huawei device,
     * after the HmsInstanceId.getInstance.getToken method is called, the onNewToken method
     * in the this class is called to obtain a token.
    * */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG, "Receive token:$token")
    }


    /*
    * todo for custom notifications
    * */
    override fun onMessageReceived(message: RemoteMessage) {
        val bodyArrays =
            message.notification.bodyLocalizationArgs
        //val key = resources.getString(R.string.body_key)
        //val body = String.format(key, bodyArrays[0], bodyArrays[1])
        Log.i(TAG, bodyArrays[0])
    }
}