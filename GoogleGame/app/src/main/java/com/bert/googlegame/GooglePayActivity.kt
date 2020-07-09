package com.bert.googlegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.billingclient.api.*

/**
 * Google支付(https://developer.android.com/google/play/billing/billing_library_overview)
 *
 */
class GooglePayActivity : AppCompatActivity() {


    lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_pay)


        billingClient = BillingClient.newBuilder(this).setListener(object : PurchasesUpdatedListener {
            override fun onPurchasesUpdated(p0: BillingResult?, p1: MutableList<Purchase>?) {

            }

        }).build()


//        billingClient.isReady

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {

            }

            override fun onBillingSetupFinished(billingResult: BillingResult?) {
                if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here
                }
            }

        })

    }
}
