package com.example.letstalkk

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.internal.Objects.ToStringHelper
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider

class OTPactivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var resendBtn : TextView
    private var otpTextView: OtpTextView? = null
    private lateinit var submitOtp : Button

    private lateinit var OTP : String
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpactivity)


        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        init()

        otpTextView?.otpListener = object :OTPListener{
            override fun onInteractionListener() {
            }
            override fun onOTPComplete(otp: String) {
                proceed(otp,OTP)
            }
        }

    }
    private fun init(){
        auth = FirebaseAuth.getInstance()
        resendBtn =findViewById(R.id.resendOtp)
        otpTextView = findViewById(R.id.otp_view)
        submitOtp = findViewById(R.id.submitOtp)
    }
    private fun proceed(code:String , OTP:String){
        val credential = PhoneAuthProvider.getCredential(OTP!!, code)
        submitOtp.setOnClickListener {
            signInWithPhoneAuthCredential(credential)
        }



    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Succesfully Signed In", "signInWithCredential:success")
                    val intent = Intent(this@OTPactivity,initialLoginProfileSetup::class.java)
                    Toast.makeText(this@OTPactivity,"Logged in Successfully !",Toast.LENGTH_LONG).show()
                    startActivity(intent)


                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Incorrect Password", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this@OTPactivity,"Incorrect OTP! Please enter the correct one",Toast.LENGTH_SHORT).show()
                    }
                    // Update UI
                }
            }
    }



}