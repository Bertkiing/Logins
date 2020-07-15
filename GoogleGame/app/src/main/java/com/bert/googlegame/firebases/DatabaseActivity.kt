package com.bert.googlegame.firebases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bert.googlegame.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * 数据库[https://firebase.google.com/docs/database/rtdb-vs-firestore?authuser=0]
 */
class DatabaseActivity : AppCompatActivity() {


    companion object{
        const val  TAG = "DatabaseActivity"
    }


    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)


        val database = Firebase.database
        reference = database.getReference("user")
        reference.setValue("Bertking")



        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG,"Failed to read.${error.toException()}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value as String
                Log.d(TAG,"Value is :$value")
            }

        })

    }


    override fun onResume() {
        super.onResume()

        reference.setValue("Bert")
    }
}
