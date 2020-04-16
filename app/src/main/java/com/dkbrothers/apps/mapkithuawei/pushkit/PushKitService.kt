package com.dkbrothers.apps.mapkithuawei.pushkit

import android.util.Log
import android.widget.Toast
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage


class PushKitService: HmsMessageService() {

    private val TAG = "PushDemoLog"

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
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.i(TAG, "Message data payload: " + remoteMessage.data)
        }
        if (remoteMessage.notification != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.notification.body)
            Toast.makeText(this,
                getString(R.string.notification_came_through_pushkit),Toast.LENGTH_LONG).show()
        }
    }


    override fun onMessageSent(s: String?) {}


    override fun onSendError(s: String?, e: Exception?) {}


}