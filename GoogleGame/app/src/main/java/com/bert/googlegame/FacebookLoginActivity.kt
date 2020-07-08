package com.bert.googlegame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_facebook_login.*
import java.lang.Exception
import java.util.*

/**
 * Facebook的登录文档(https://developers.facebook.com/docs/facebook-login/android)
 * 官方Demo:[https://github.com/facebook/facebook-android-sdk/tree/master/samples/FBLoginSample/src/main/java/com/facebook/fbloginsample]
 */
class FacebookLoginActivity : AppCompatActivity() {
    val TAG = "FacebookLoginActivity"

    lateinit var callbackManager: CallbackManager

    lateinit var accessTokTokenTracker: AccessTokenTracker

    lateinit var profileTracker: ProfileTracker

    var isLoginIn:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)

        // 创建 callbackManager，以便处理登录响应。
        callbackManager = CallbackManager.Factory.create()


        accessTokTokenTracker = object :AccessTokenTracker(){
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {

            }

        }


        profileTracker = object :ProfileTracker(){
            override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {

            }
        }





//        loginManagerRegCallback()


        /**
         * 可以通过检查 AccessToken.getCurrentAccessToken() 和 Profile.getCurrentProfile() 来查看用户是否已登录。
         * 获取用户信息
         */
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            Log.d(TAG,"name : ${profile.name}")
            Log.d(TAG,"id ${profile.id}")
            Log.d(TAG,"linkUri ${profile.linkUri}")
            Log.d(TAG,"pic-url ${profile.getProfilePictureUri(100,100)}")
        }



        val accessToken = AccessToken.getCurrentAccessToken()
        Log.d(TAG,"获取当前访问口令相关联的权限列表:${accessToken?.permissions}")

        Log.d(TAG,"获取当前访问口令拒绝的权限列表:${accessToken?.permissions}")

        Log.d(TAG,"userId : ${accessToken?.userId}")

        Log.d(TAG,"token：${accessToken?.token}")



        if (accessToken == null || accessToken.isExpired) {
            tv_login_state?.text = "用户未登录"
            isLoginIn = false
        } else {
            tv_login_state?.text = "登录成功"
            isLoginIn = true
        }

        /**
         * 需要注意的是 Facebook登录 元素是 LoginButton
         * LoginButton 是一个界面元素，其中包含 LoginManager 具备的功能。用户点击按钮后，就会以 LoginManager 中设置的权限开始登录。
         * 按钮随登录状态变化，并根据用户的身份验证状态显示正确文本。
         *
         * 如果使用LoginButton的setOnClickerListener结合LoginManager使用，会吊起登录两次，需要注意
         */
        btn_fb_login?.authType = "rerequest"
        btn_fb_login?.setPermissions(Arrays.asList("public_profile","email"))
        btn_fb_login?.registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                setResult(Activity.RESULT_OK)
                finish()
            }

            override fun onCancel() {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

            override fun onError(error: FacebookException?) {
                Log.d(TAG,"登录发生错误:${error?.message}")
            }


        })

        /**
         * 使用LoginButton进行登录
         */
        tv_login_out?.setOnClickListener {
            LoginManager.getInstance().logOut()
            Utils.showToast(this,"退出登录")
        }

        /**
         * 使用 LoginManager进行登录
         */
        btn_fb_login_manager?.setOnClickListener {
            if(AccessToken.getCurrentAccessToken() == null){
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email","user_likes"))

//                quickLogin()

            }
        }

    }

    /**
     * 快捷登录
     */
    private fun quickLogin() {
        LoginManager.getInstance().retrieveLoginStatus(this, object : LoginStatusCallback {
            override fun onFailure() {

            }

            override fun onError(exception: Exception?) {

            }

            override fun onCompleted(accessToken: AccessToken?) {

            }
        })
    }


    /**
     * 为了响应登录结果，我们需要使用 LoginManager 或 LoginButton 注册回调
     * 此处使用：LoginManager注册回调
     *
     * 登录配合使用： LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
     */
    private fun loginManagerRegCallback() {
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "FB登录成功：${result.toString()}")
                isLoginIn = true
                Log.d(TAG, "AccessToken : ${result?.accessToken}")
                Utils.showToast(this@FacebookLoginActivity, "登录成功")


                // 刷新token的操作
                AccessToken.refreshCurrentAccessTokenAsync(object :AccessToken.AccessTokenRefreshCallback{
                    override fun OnTokenRefreshed(accessToken: AccessToken?) {
                        // 刷新成功
                    }

                    override fun OnTokenRefreshFailed(exception: FacebookException?) {
                        // 刷新失败
                    }
                })



            }

            override fun onCancel() {
                isLoginIn = false
                Utils.showToast(this@FacebookLoginActivity, "取消登录")
            }

            override fun onError(error: FacebookException?) {
                isLoginIn = false
                Log.d(TAG, "FB登录错误: ${error?.message} , ${error?.toString()}")
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 集成到 FacebookSDK 登录或分享功能的所有活动和片段都应将 onActivityResult 转发到 callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        accessTokTokenTracker.stopTracking()
        profileTracker.stopTracking()
    }
}
