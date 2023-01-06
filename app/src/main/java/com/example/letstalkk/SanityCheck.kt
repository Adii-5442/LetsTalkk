package com.example.letstalkk

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class IncorrectLengthInputsException(message :String):Exception(message)
class EmptyArraysException(message: String):Exception(message)

class RegexConstants {
    companion object{
        val ALPHABETIC:Regex="[a-zA-Z.? ]*".toRegex()
        val ALPHANUMERIC:Regex="[a-zA-Z0-9.? ]*".toRegex()
        val ALPHANUMERIC_LOWERCASE:Regex="[a-z0-9.? ]*".toRegex()
        val EMAIL_DOMAIN:Regex="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
    }
}

class SanityCheckTexts(

    displays:Array<TextView>,
    textFields:Array<EditText>,
    constraints:Array<Regex>,
    selectedDate:Calendar,
    dateButton:Button,
    displayDate:TextView,
    selectedGender:String,
    displayGender: TextView

): AppCompatActivity() {
    private lateinit var displays: Array<TextView>
    private lateinit var textFields: Array<EditText>
    private lateinit var constraints: Array<Regex>
    private lateinit var selectedDate: Calendar
    private lateinit var dateButton: Button
    private lateinit var selectedGender: String
    private lateinit var displayGender: TextView
    private lateinit var displayDate: TextView
    init {
        try {
            if(displays.isEmpty() and textFields.isEmpty() and constraints.isEmpty())
                throw EmptyArraysException("All the arrays are empty.")
            else if (!((displays.size==textFields.size)and(textFields.size==constraints.size)))
                throw IncorrectLengthInputsException("The array inputs are not of equal size.")
            else{
                this.displays=displays
                this.textFields=textFields
                this.constraints=constraints
                this.selectedDate=selectedDate
                this.dateButton=dateButton
                this.selectedGender=selectedGender
                this.displayDate=displayDate
                this.displayGender=displayGender
            }
        }
        catch (error:IncorrectLengthInputsException){
            println(error.message)
        }
        catch (error:EmptyArraysException){
            println(error.message)
        }
    }


    private fun alphabeticCheck(textField:EditText,display:TextView):Boolean{
        if (!textField.text.toString().matches(RegexConstants.ALPHABETIC)){
            display.text="This field can contain alphabetic characters only."
            return false
        }
        return true
    }

    private fun alphaNumericCheck(textField:EditText,display:TextView):Boolean{
        if(!textField.text.toString().matches(RegexConstants.ALPHANUMERIC)){
            display.text="This field can contain alphanumeric characters only."
            return false
        }
        return true
    }


    private fun lowerCaseAlphaNumericCheck(textField:EditText,display:TextView):Boolean{
        if(!textField.text.toString().matches(RegexConstants.ALPHANUMERIC_LOWERCASE)){
            display.text="This field can contain lowercase alphanumeric characters only."
            return false
        }
        return true
    }

    private fun domainCheck(textField:EditText,display:TextView):Boolean{
        if(!textField.text.toString().matches(RegexConstants.EMAIL_DOMAIN)){
            display.text="This field must contain a domain name followed by '@'"
            return false
        }
        return true
    }

    private fun dateCheck(display: TextView):Boolean{
        val myFormat = "dd/MM/yyyy"// mention the format you need
        val currentDate :Calendar=Calendar.getInstance()
        if(selectedDate > currentDate){
            display.text="No time travellers allowed."
            return false
        }
        if(dateButton.text==myFormat){
            display.text="This field cannot be empty."
            return false
        }
        display.text=""
        return true
    }

    private fun genderCheck(display: TextView):Boolean{
        if(selectedGender=="--Select--"){
            display.text="This field cannot be empty."
            return false
        }
        display.text=""
        return true
    }


    fun checkSanity():Boolean{
        var checksOut:Boolean=true
        //Checks for Full name, Username, Email are run below
        for (index in displays.indices){
            if(textFields[index].text.isEmpty()){
                displays[index].text ="This field cannot be empty"
                checksOut=false
                continue
            }
            when(this.constraints[index]){
                RegexConstants.ALPHABETIC-> checksOut=alphabeticCheck(textFields[index],displays[index])
                RegexConstants.ALPHANUMERIC->checksOut=alphaNumericCheck(textFields[index],displays[index])
                RegexConstants.ALPHANUMERIC_LOWERCASE->checksOut=lowerCaseAlphaNumericCheck(textFields[index],displays[index])
                RegexConstants.EMAIL_DOMAIN->checksOut=domainCheck(textFields[index],displays[index])
            }
            if(checksOut) displays[index].text =""
        }
        //Checks for gender are run below
        checksOut=genderCheck(displayGender)
        //Checks for date are run below
        checksOut=dateCheck(displayDate)
        return checksOut
    }
}