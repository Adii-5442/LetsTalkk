package com.example.letstalkk

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.letstalkk.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth

class EditProfile : AppCompatActivity() {

    private lateinit var editPicture : FloatingActionButton
    private lateinit var pictueToolBar : CollapsingToolbarLayout

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(findViewById(R.id.toolbar))

        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null) {
            Log.i("USER UID","${user.uid}")
        }


        pictueToolBar = findViewById(R.id.toolbar_layout)
        editPicture = findViewById(R.id.editPic)
        editPicture.setOnClickListener { view ->
           pictueToolBar.background = getDrawable(R.drawable.couple)
            Snackbar.make(view, "Pick a nice Picture of yours", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}