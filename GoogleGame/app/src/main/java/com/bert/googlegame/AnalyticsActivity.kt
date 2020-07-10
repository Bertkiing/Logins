package com.bert.googlegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_analytics.*

/**
 * Google Analytics
 */
class AnalyticsActivity : AppCompatActivity() {


    private lateinit var firebaseAnalytics: FirebaseAnalytics


    companion object {
        private const val TAG = "AnalyticsActivity"

        val sunwukong = RoleInfo(1L,"孙悟空",R.drawable.ic_dragon_ball)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        firebaseAnalytics = Firebase.analytics


        btn_record_image?.setOnClickListener {
            recordImageView()
        }


        btn_share?.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            val text = "Hello, welcome to here"
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)

//            firebaseAnalytics.logEvent("share_event"){
            // 分享事件
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE){
                param("name", sunwukong.name)
                param("text",text)
            }

        }

    }

    override fun onResume() {
        super.onResume()
        recordScreenView()
    }


    private fun  recordImageView(){
        val id = sunwukong.id
        val name = sunwukong.name

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
            param(FirebaseAnalytics.Param.ITEM_NAME, name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }
    }

    private fun recordScreenView(){
        firebaseAnalytics.setCurrentScreen(this,TAG,null)
    }

}
