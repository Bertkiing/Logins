package com.bert.googlegame.firebases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bert.googlegame.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.android.synthetic.main.activity_google_ad.*

@SuppressWarnings("unused", "lambda")
class GoogleADActivity : AppCompatActivity() {

    lateinit var mAdView: AdView

    lateinit var adLoader: AdLoader


    companion object {
        const val TAG = "GoogleADActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_ad)

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)

        initNativeAd()
    }

    private fun initNativeAd() {
      adLoader =   AdLoader.Builder(this, getString(R.string.test_native_ad))
            .forUnifiedNativeAd {ad:UnifiedNativeAd ->
                // 展示广告
                unifiedNativeAdView?.setNativeAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    // 处理 错误
                }
            }).withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()


        adLoader.loadAds(AdRequest.Builder().build(),1)
    }
}
