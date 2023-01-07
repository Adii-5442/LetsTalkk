package com.example.letstalkk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class PhoneNumber : AppCompatActivity() {

    private lateinit var phoneNumber :TextInputEditText
    private lateinit var proceedPhone : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var number : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_phone_number)

        init()
        Log.d("Number","$phoneNumber")
        proceedPhone.setOnClickListener {
            number = phoneNumber.text?.trim().toString()
            if(number.isNotEmpty()){
                if(number.length == 10){
                    Toast.makeText(this,"Please wait ..",Toast.LENGTH_LONG).show()
                    number = "+91$number"
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }else{
                    Toast.makeText(this,"Please enter a valid number ",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Please enter a valid number, it can't be blank like you ",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun init(){
        phoneNumber = findViewById<TextInputEditText>(R.id.phone_input)
        proceedPhone = findViewById<Button>(R.id.proceedBtn1)
        auth = FirebaseAuth.getInstance()

//        proceedPhone.setOnClickListener {
//            startActivity(
//                Intent(this, OTPactivity::class.java)
//                    .putExtra("phoneNumber", phoneNumber.text.toString())
//            )
//        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Authentication Successful",Toast.LENGTH_SHORT).show()
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Log.d("ex","exception at SignInwITHpHONEAUth")
                    }
                    // Update UI
                }
            }
    }
    private fun sendToMain(){
        startActivity(Intent(this , MainActivity::class.java))
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("TAG","onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("Error","onVerificationFailed:${e.toString()}")
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("Error","TOO MANY MESSAGES FOR UR PROJECT:${e.toString()}")
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("Tag", "onCodeSent:$verificationId")
            Toast.makeText(this@PhoneNumber,"Verification Code has been sent on your mobile number",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@PhoneNumber,OTPactivity::class.java)
            intent.putExtra("OTP",verificationId)
            intent.putExtra("resendToken",token)
            intent.putExtra("phoneNumber",number)
            startActivity(intent)

            // Save verification ID and resending token so we can use them later
        }
    }

    override fun onStart() {
      super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this , MainActivity::class.java))
        }
    }

}