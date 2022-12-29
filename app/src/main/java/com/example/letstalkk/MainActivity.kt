package com.example.letstalkk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var signOutBtn:Button
    private  lateinit var gToAct : Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        signOutBtn = findViewById(R.id.signOutBtn)
        signOutBtn.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,PhoneNumber::class.java))
        }
        gToAct = findViewById(R.id.goToAct)
        gToAct.setOnClickListener {
//            startActivity(Intent(this,EditProfile::class.java)) //use it when edit profile is created
        }

    }
}