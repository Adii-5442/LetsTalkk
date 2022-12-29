package com.example.letstalkk

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.letstalkk.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditProfile : AppCompatActivity() {

    private lateinit var editPicture : FloatingActionButton
    private lateinit var pictueToolBar : CollapsingToolbarLayout
    private lateinit var database : FirebaseDatabase
    private lateinit var db : FirebaseFirestore

    private var user : FirebaseUser? = FirebaseAuth.getInstance().currentUser


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(findViewById(R.id.toolbar))


        init()

        if(user!=null) {
            Log.i("USER UID","${user?.uid}")
        }
        editPicture.setOnClickListener { view ->
            //pushDataToFirebase()
           pictueToolBar.background = getDrawable(R.drawable.couple)
            Snackbar.make(view, "Pick a nice Picture of yours", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
    private fun init(){
        pictueToolBar = findViewById(R.id.toolbar_layout)
        editPicture = findViewById(R.id.editPic)
        db = FirebaseFirestore.getInstance()
    }
    private fun pushDataToFirebase(){
//        val data = hashMapOf(
//            "name" to "aditya",
//            "dateOfBirth" to "28-11-2002"
//        )
//        db.collection("users").document("dummy").set(data)




    }

}