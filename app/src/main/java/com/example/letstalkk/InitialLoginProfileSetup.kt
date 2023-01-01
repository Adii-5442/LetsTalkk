package com.example.letstalkk

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.letstalkk.databinding.ActivityInitialLoginProfileSetupBinding
import android.view.View
import android.widget.*
import android.app.DatePickerDialog
import android.content.Intent
import java.text.SimpleDateFormat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class InitialLoginProfileSetup : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityInitialLoginProfileSetupBinding
    private lateinit var fullName: EditText
    private lateinit var userName : EditText
    private lateinit var eMail : EditText
    private lateinit var genderSpinner:Spinner
    private lateinit var dateOfBirth : Button
    private lateinit var profilePicGalleryButton : FloatingActionButton
    private lateinit var profilePicCameraButton:FloatingActionButton
    private lateinit var profilePicDisplay: ImageView
    private lateinit var finalButton : ExtendedFloatingActionButton
    private var cal = Calendar.getInstance()

    //Function to update button to display the chosen birth date by the user
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateOfBirth.text = sdf.format(cal.time)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInitialLoginProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Profile Setup"

        //Input Variables
        fullName=findViewById<EditText>(R.id.editTextFullName)
        userName=findViewById<EditText>(R.id.editTextUserName)
        eMail=findViewById<EditText>(R.id.editTextEmail)
        dateOfBirth=findViewById<Button>(R.id.dateOfBirth)
        genderSpinner = findViewById<Spinner>(R.id.genderSelect)

        profilePicGalleryButton=findViewById<FloatingActionButton>(R.id.editProfilePicGallery)
        profilePicCameraButton=findViewById<FloatingActionButton>(R.id.editProfilePicCamera)
        profilePicDisplay=findViewById<ImageView>(R.id.profilePicShow)

        finalButton=findViewById<ExtendedFloatingActionButton>(R.id.finalizeProfile)


        //Pre defined Variables
        val genders=resources.getStringArray(R.array.Genders)
        val defaultDate="__/__/____"


        //Initialize Spinner with gender array
        if (genderSpinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.spinner_item, genders)
            genderSpinner.adapter = adapter

            genderSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    //position holds the index of the selected element in gender array
                    //When a gender is Selected, use genders[position] to fetch the selected gender by user
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        //Date picker
        dateOfBirth.text=defaultDate
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //This is where we can extract the user filled date of birth
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        dateOfBirth.setOnClickListener {
            DatePickerDialog(
                this@InitialLoginProfileSetup,
                dateSetListener,
                // Sets DatePickerDialog to point to previous date which was set to cal variable
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        //Image Picker

        profilePicGalleryButton.setOnClickListener{
            if (EasyPermissions.hasPermissions(this,android.Manifest.permission.CAMERA)){
                pickImageFromGallery()
            }
            else{
                EasyPermissions.requestPermissions(
                    this,
                    "The App needs permission to use camera.",
                    101,
                    android.Manifest.permission.CAMERA
                )
            }
        }

        profilePicCameraButton.setOnClickListener {
            if (EasyPermissions.hasPermissions(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                pickImageFromCamera()
            }
            else{
                EasyPermissions.requestPermissions(
                    this,
                    "The App needs permission to access storage.",
                    100,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }


    private fun pickImageFromGallery(){

        ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*")).crop().maxResultSize(400,400).start()

    }

    private fun pickImageFromCamera(){

        ImagePicker.with(this).cameraOnly().crop().start()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode==100){
            //Means permission has been granted for gallery
            pickImageFromGallery()
        }
        else if (requestCode==101){
            //Means permission has been granted for camera
            pickImageFromCamera()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }else{
            Toast.makeText(applicationContext,"Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK&&data!=null){
                profilePicDisplay.setImageURI(data?.data)
        }
    }
}





