package com.dkbrothers.apps.mapkithuawei.remoteconfiguration

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.agconnect.remoteconfig.AGConnectConfig
import kotlinx.android.synthetic.main.activity_remote_configuration.*


class RemoteConfigurationActivity : AppCompatActivity() {

    private lateinit var remoteConfig:AGConnectConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_configuration)
        remoteConfig = AGConnectConfig.getInstance()
        applyNow()
    }


    //TODO:-------------------------- Loading Process ----------------------------------

    //Applying parameter values immediately
    private fun applyNow(){
        remoteConfig.fetch().addOnSuccessListener { configValues ->
            remoteConfig.apply(configValues)
            // Call methods such as getString() to obtain applied values.
            changeBackgroundColor(remoteConfig.getValueAsString("backgroundColor"))
            if(remoteConfig.getValueAsBoolean("activeColorBlue"))
                btn_go_test.visibility = View.VISIBLE
        }.addOnFailureListener { }
    }

    //Applying parameter values upon the next startup
    private fun applyNextStartup(){
        val lastConfigValues = remoteConfig.loadLastFetched()
        remoteConfig.apply(lastConfigValues)
        remoteConfig.fetch()
       // Call methods such as getString() to obtain applied values.
        changeBackgroundColor(remoteConfig.getValueAsString("backgroundColor"))
        if(remoteConfig.getValueAsBoolean("activeColorBlue"))
            btn_go_test.visibility = View.VISIBLE
    }

    private fun changeBackgroundColor(colorId:String){
        main_view.setBackgroundColor(Color.parseColor(colorId))
    }

     fun onActiveBlue(v:View){
         main_view.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent))
    }

    fun onGoDocumentation(view: View) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.remote_config_documentation_link)))
        )
    }

}
