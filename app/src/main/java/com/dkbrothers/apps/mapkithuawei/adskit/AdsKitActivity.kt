package com.dkbrothers.apps.mapkithuawei.adskit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_ads_kit.*

/**
 * Docs
 * https://developer.huawei.com/consumer/en/hms/huawei-adskit
 * https://developer.huawei.com/consumer/en/community/codelabs
 * */
class AdsKitActivity : AppCompatActivity() {

    private val TAG = "AdsKitActivity"

    private lateinit var interstitialAd:InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_kit)
        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this)
        loadBannerAd()
        loadInterstitialAd()
    }

    private fun loadBannerAd(){
        val adParam = AdParam.Builder().build()
        hw_banner_view.loadAd(adParam)
    }


    private fun loadInterstitialAd() {
        interstitialAd =  InterstitialAd(this)
        // Set an ad slot ID.
        interstitialAd.adId = "teste9ih9j0rc3" //For image_ad_id is teste9ih9j0rc3 - video_ad_id is testb4znbuh3n2.
        interstitialAd.adListener = adListener
        // Load an interstitial ad.
        val adParam = AdParam.Builder().build()
        interstitialAd.loadAd(adParam)
    }


    fun showInterstitial(v:View) {
        // Display an interstitial ad.
        if (interstitialAd.isLoaded) {
            interstitialAd.show()
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }

    private val adListener: AdListener = object : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            Toast.makeText(this@AdsKitActivity, "Ad loaded", Toast.LENGTH_SHORT).show()
        }

        override fun onAdFailed(errorCode: Int) {
            Toast.makeText(
                this@AdsKitActivity, "Ad load failed with error code: $errorCode",
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "Ad load failed with error code: $errorCode")
        }

        override fun onAdClosed() {
            super.onAdClosed()
            Log.d(TAG, "onAdClosed")
        }

        override fun onAdClicked() {
            Log.d(TAG, "onAdClicked")
            super.onAdClicked()
        }

        override fun onAdOpened() {
            Log.d(TAG, "onAdOpened")
            super.onAdOpened()
        }
    }

}