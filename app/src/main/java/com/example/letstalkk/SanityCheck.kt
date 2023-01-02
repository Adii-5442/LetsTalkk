package com.example.letstalkk

import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IncorrectLengthInputsException(message :String):Exception(message)
class EmptyArraysException(message: String):Exception(message)

class RegexConstants {
    companion object{
    val ALPHABETIC:Regex="[a-zA-Z.? ]*".toRegex()
    val ALPHANUMERIC:Regex="[a-zA-Z0-9.? ]*".toRegex()
    val ALPHANUMERIC_LOWERCASE:Regex="[a-z0-9.? ]*".toRegex()
    val DOMAIN:Regex="[@]".toRegex()
    }
}

class SanityCheck(displays:Array<TextView>, textFields:Array<EditText>, constraints:Array<Regex>):AppCompatActivity() {
    private lateinit var displays: Array<TextView>
    private lateinit var textFields: Array<EditText>
    private lateinit var constraints: Array<Regex>
    init {
        try {
            if(displays.isEmpty() and textFields.isEmpty() and constraints.isEmpty())
                throw EmptyArraysException("All the arrays are empty.")
            else if (!((displays.size==textFields.size)and(textFields.size==constraints.size)))
                throw IncorrectLengthInputsException("The array inputs are not of equal size.")
            else{ this.displays=displays
                this.textFields=textFields
                this.constraints=constraints
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
        if (!textField.text.toString().matches("[a-zA-z.? ]*".toRegex())){
            display.text="This field can contain alphabetic characters only."
            return false
        }
        return true
    }

    private fun alphaNumericCheck(textField:EditText,display:TextView):Boolean{
        if(!textField.text.toString().matches("[a-zA-Z0-9.? ]*".toRegex())){
            display.text="This field can contain alphanumeric characters only."
            return false
        }
        return true
    }


    private fun lowerCaseAlphaNumericCheck(textField:EditText,display:TextView):Boolean{
        if(!textField.text.toString().matches("[a-z0-9.? ]*".toRegex())){
            display.text="This field can contain lowercase alphanumeric characters only."
            return false
        }
        return true
    }

    private fun domainCheck(textField:EditText,display:TextView):Boolean{
        if(!textField.text.toString().matches(RegexConstants.DOMAIN)){
            display.text="This field must contain a domain name followed by '@'"
            return false
        }
        return false
    }


    fun checkSanity(){
        for (index in displays.indices){
            if(textFields[index].text.isEmpty()){
                displays[index].text ="This field Cannot be Empty"
                continue
            }
            var checksOut:Boolean=true
            when(constraints[index]){
                RegexConstants.ALPHABETIC-> checksOut=alphabeticCheck(textFields[index],displays[index])
                RegexConstants.ALPHANUMERIC->checksOut=alphaNumericCheck(textFields[index],displays[index])
                RegexConstants.ALPHANUMERIC_LOWERCASE->checksOut=lowerCaseAlphaNumericCheck(textFields[index],displays[index])
                RegexConstants.DOMAIN->checksOut=domainCheck(textFields[index],displays[index])
            }
            if(checksOut) displays[index].text =""
        }
    }
}