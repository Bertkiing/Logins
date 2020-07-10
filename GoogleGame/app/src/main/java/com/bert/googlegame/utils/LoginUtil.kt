package com.bert.googlegame.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.firebase.ui.auth.AuthUI

/**
 *
 * @Author: bertking
 * @ProjectName: GoogleGame
 * @CreateAt: 2020-07-10 14:59
 * @UpdateAt: 2020-07-10 14:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description:
 */
object LoginUtil {

    const val TAG = "LoginUtil"

     const val RC_SIGN_IN = 0

    /**
     * Sign in with FirebaseUI Auth.
     */
     fun triggerSignIn(activity: Activity) {
        Log.d(TAG, "Attempting SIGN-IN!")

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
//            AuthUI.IdpConfig.FacebookBuilder().build(),
//            AuthUI.IdpConfig.TwitterBuilder().build()
        )

        activity.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(true)
                .setTosAndPrivacyPolicyUrls("https://superapp.example.com/terms-of-service.html","https://superapp.example.com/privacy-policy.html")
                .build(),
            RC_SIGN_IN
        )
    }

    /**
     * Sign out with FirebaseUI Auth.
     */
     fun triggerSignOut(context: Context) {
        AuthUI.getInstance().signOut(context).addOnCompleteListener {
            Log.d(TAG, "User SIGNED OUT!")
        }
    }


     fun delete(context: Context){
        AuthUI.getInstance().delete(context).addOnCompleteListener {
            Log.d(TAG, "User DELETE!")
        }
    }
}