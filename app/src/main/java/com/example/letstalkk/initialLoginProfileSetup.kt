package com.example.letstalkk

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.letstalkk.databinding.ActivityInitialLoginProfileSetupBinding
import android.view.View
import android.widget.*
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import android.widget.DatePicker
import java.util.*

class initialLoginProfileSetup : AppCompatActivity() {

    private lateinit var binding: ActivityInitialLoginProfileSetupBinding
    private lateinit var fullName: EditText
    private lateinit var userName : EditText
    private lateinit var eMail : EditText
    private lateinit var genderSpinner:Spinner
    private lateinit var dateOfBirth : Button
    private var cal = Calendar.getInstance()


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


        //Pre defined Variable in values/string folder
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

    }
}