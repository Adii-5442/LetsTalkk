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
import android.net.Uri
import java.text.SimpleDateFormat
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class initialLoginProfileSetup : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityInitialLoginProfileSetupBinding
    private lateinit var fullName: EditText
    private lateinit var userName : EditText
    private lateinit var eMail : EditText
    private lateinit var genderSpinner:Spinner
    private lateinit var dateOfBirth : Button
    private lateinit var profilePic : FloatingActionButton
    private lateinit var finalButton : ExtendedFloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var cal = Calendar.getInstance()
    private var arraylist = ArrayList<Uri>();

    //Function to update button to display the chosen birth date by the user
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateOfBirth.text = sdf.format(cal.getTime())
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
        profilePic=findViewById<FloatingActionButton>(R.id.editProfilePic)
        finalButton=findViewById<ExtendedFloatingActionButton>(R.id.finalizeProfile)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView)


        //Pre defined Variables
        val genders=resources.getStringArray(R.array.Genders)
        val defaultDate="__/__/___"


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
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                //This is where we can extract the user filled date of birth
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        dateOfBirth.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@initialLoginProfileSetup,
                    dateSetListener,
                    // Sets DatePickerDialog to point to previous date which was set to cal variable
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


        //Image Picker
        profilePic.setOnClickListener({
            if (EasyPermissions.hasPermissions(this,android.Manifest.permission.CAMERA) and EasyPermissions.hasPermissions(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                imagePicker();
            }
            else{
                EasyPermissions.requestPermissions(
                    this,
                    "Bhai permission dede camera ki",
                    100,
                    android.Manifest.permission.CAMERA
                );
                EasyPermissions.requestPermissions(
                    this,
                    "Bhai permission dede storage ki",
                    100,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                );
            }
        })
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
        if (requestCode==100 && perms.size==2){


            imagePicker()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){


            AppSettingsDialog.Builder(this).build().show()
        }else{

            Toast.makeText(applicationContext,"Permission Demied",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== RESULT_OK&&data!=null){


            if(requestCode==FilePickerConst.REQUEST_CODE_PHOTO){


                arraylist= data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)!!


                recyclerView.layoutManager=LinearLayoutManager(this)

                recyclerView.adapter= FilePickerClass(arraylist)
            }
        }
    }



    private fun imagePicker(){
        FilePickerBuilder.instance
            .setActivityTitle("Select Image")
            .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN,3)
            .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN,4)
            .setMaxCount(1)
            .setSelectedFiles(arraylist)
            .setActivityTheme(R.style.FilePickerTheme)
            .pickPhoto(this);
    }
}