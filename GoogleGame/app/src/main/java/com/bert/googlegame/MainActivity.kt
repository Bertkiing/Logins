package com.bert.googlegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

/**
 * https://developers.google.com/identity/sign-in/android/sign-in
 * 添加Google登录工作流
 */
class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    companion object{
        private val RC_SIGN_IN = 9001

    }


    private var mGoogleSignInClient: GoogleSignInClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

         mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        btn_sign_in?.setOnClickListener {
            signIn()
        }

        btn_login_out?.setOnClickListener {
           signOut()
        }

        btn_remove_access?.setOnClickListener {
            removeAccess()
        }

        btn_fb_login?.setOnClickListener {
            startActivity(Intent(this,FacebookLoginActivity::class.java))
        }

    }


    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)

        Log.d(TAG,"account info :$account")
        printInfo(account)

        updateUI(account)
    }


    private fun printInfo(account:GoogleSignInAccount?){
        Log.d(TAG,"Granted Scope: ${account?.grantedScopes}")
        Log.d(TAG,"Display Name : ${account?.displayName}")
        Log.d(TAG,"Account : ${account?.account}")
        Log.d(TAG,"Email : ${account?.email}")
        Log.d(TAG,"Photo url : ${account?.photoUrl}")
        Log.d(TAG,"ID : ${account?.id}")
        Log.d(TAG,"IdToken : ${account?.idToken}")
    }


    /**
     * 登录
     */
    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    /**
     * 退出登录
     */
    private fun signOut(){
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this,object :OnCompleteListener<Void>{
            override fun onComplete(task: Task<Void>) {
                updateUI(null)
            }

        })
    }

    /**
     * 退出账号
     * It is highly recommended that you provide users that signed in with Google the ability to disconnect their Google account from your app.
     * If the user deletes their account, you must delete the information that your app obtained from the Google APIs.
     */
    private fun removeAccess(){
        mGoogleSignInClient?.revokeAccess()?.addOnCompleteListener(this,object :OnCompleteListener<Void>{
            override fun onComplete(task: Task<Void>) {
                updateUI(null)
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask:Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        }catch (e:ApiException){
            Log.d(TAG,"signInResult:failed code = "+e.statusCode)
            updateUI(null)
        }
    }


     fun updateUI(account:GoogleSignInAccount?){
        if(account != null){
            tv_login_state?.text = account.displayName
            btn_sign_in?.visibility = View.GONE
        }else{
            tv_login_state?.text = "未登录"
            btn_sign_in?.visibility = View.VISIBLE
        }
    }

}
