package com.example.testmcc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var image: Uri? = null
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        Firebase.auth.signOut()










        analytics = Firebase.analytics
        getRegToken()
        screen_Track("MainActivity", "home")
        Log.e("ola", "test")
        btn_choose.setOnClickListener {
            val i = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, 100)

        }

        var randomNumber = UUID.randomUUID().toString()
        val storageRef = FirebaseStorage.getInstance()
            .getReference("images")
        btn_uplode.setOnClickListener {
            val imageRef = storageRef.child("image" + randomNumber)
            imageRef.putFile(image!!).addOnSuccessListener {
                Log.d("TAG", "Success: ");
            }.addOnFailureListener {
                Log.d("TAG", "Success: ");
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            image = data!!.data
            imageView.setImageURI(data.data)
        }
    }

    fun select_content(id: String, name: String, contentType: String) {
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
            param(FirebaseAnalytics.Param.ITEM_NAME, name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        }
    }

    fun screen_Track(screenClass: String, ScreenName: String) {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
            param(FirebaseAnalytics.Param.SCREEN_NAME, ScreenName)
        }


    }

    fun custemEvent() {
        analytics.logEvent("imageShare") {
            param("nameIamge", "ola.png")
            param("shareFrom", "MainActivity")

        }

    }

    fun getRegToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("ola",
                    "Fetching FCM registration token failed",
                    task.exception)
                return@addOnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.e("token", token.toString())

        }
    }


}