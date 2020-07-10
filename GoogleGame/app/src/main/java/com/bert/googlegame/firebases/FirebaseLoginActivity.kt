package com.bert.googlegame.firebases

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bert.googlegame.R
import com.bert.googlegame.utils.LoginUtil
import com.bert.googlegame.utils.Utils
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_firebase_login.*

/**
 * 使用Firebase 进行登录操作
 */
class FirebaseLoginActivity : AppCompatActivity() {

    companion object{
        const val TAG = "FirebaseLoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_login)

        val user = FirebaseAuth.getInstance().currentUser
        updateUI(user)
        if(user == null){
            LoginUtil.triggerSignIn(this)
        }

        btn_login_out?.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                Utils.showToast(this,"退出登录")
                updateUI(null)
            }
        }


        tv_login_state?.setOnClickListener {
            LoginUtil.triggerSignIn(this)
        }

    }


    private fun updateUI(user: FirebaseUser?){
        if(user == null){
            btn_login_out?.visibility = View.GONE
            tv_login_state?.text = "未登录"
            tv_login_state?.isEnabled = true
        }else{
            btn_login_out?.visibility = View.VISIBLE
            tv_login_state?.text = "登录成功"
            tv_login_state?.isEnabled = false
            Log.d(TAG, "CURRENT user: " + user.email + " " + user.displayName)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == LoginUtil.RC_SIGN_IN){

            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                // 登录成功( successfully signed in)
                val user = FirebaseAuth.getInstance().currentUser
                updateUI(user)
                Log.d(TAG,"userInfo : ${user.toString()}")
                Utils.showToast(this,"登录成功")

            }else{
                // 登录失败(Sign in failed)
                if(response == null){
                       Log.d(TAG,"error:登录取消登录")
                }else{
                    Log.d(TAG,"error:${response.error?.errorCode}")
                }
            }
        }

    }
}
