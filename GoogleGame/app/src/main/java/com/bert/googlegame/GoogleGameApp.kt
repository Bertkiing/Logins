package com.bert.googlegame

import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds

/**
 *
 * @Author: bertking
 * @ProjectName: Googles
 * @CreateAt: 2020-07-07 11:35
 * @UpdateAt: 2020-07-07 11:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description:
 */
class GoogleGameApp : MultiDexApplication() {

    companion object{
        const val TAG = "GoogleGameApp"
    }

    override fun onCreate() {
        super.onCreate()
        // 初始化 SDK
//        MobileAds.initialize(this,"ca-app-pub-4451195260777809~2946630995")
    }
}