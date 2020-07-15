package com.bert.googlegame

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {
     val  TAG = this::class.java.simpleName

    lateinit var context: Context
    lateinit var activity:Activity


    abstract fun layoutId():Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        context = this
        activity = this
    }
}
