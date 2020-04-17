package com.dkbrothers.apps.mapkithuawei.remoteconfiguration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.agconnect.remoteconfig.AGConnectConfig


class RemoteConfigurationActivity : AppCompatActivity() {

    private var remoteConfig:AGConnectConfig?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_configuration)
        remoteConfig = AGConnectConfig.getInstance()
        remoteConfig?.applyDefault(R.xml.remote_config)
    }

}
