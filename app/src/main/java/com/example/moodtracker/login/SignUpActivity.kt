package com.example.moodtracker.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.moodtracker.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        signUpBtn.setOnClickListener {
            signUpUser()
        }


    }

    private fun signUpUser() {
        //check if email box is empty
        if(enterNewEmail.text.toString().isEmpty()) {
            enterNewEmail.error = "Please enter email"
            enterNewEmail.requestFocus()
        }

        //check if email exists in database
        if(!Patterns.EMAIL_ADDRESS.matcher(enterNewEmail.text.toString()).matches()) {
            enterNewEmail.error = "Please enter valid email"
            enterNewEmail.requestFocus()
        }

        //check if password box is empty
        if(enterNewPassword.text.toString().isEmpty()) {
            enterNewEmail.error = "Please enter password"
            enterNewEmail.requestFocus()
        }

        //authenticate user
        auth.createUserWithEmailAndPassword(enterNewEmail.text.toString(), enterNewPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(baseContext, "Register successful, you may now login", Toast.LENGTH_SHORT).show()

                    val user = auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Register failed", Toast.LENGTH_SHORT).show()
                }

            }
    }





}