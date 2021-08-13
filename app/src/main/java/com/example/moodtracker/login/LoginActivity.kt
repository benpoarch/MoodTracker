package com.example.moodtracker.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Activity to log in a user using Firebase Authentication
 * @author Ben Poarch
 */
class LoginActivity : AppCompatActivity() {

    //initialise FirebaseAuth variable
    private lateinit var auth: FirebaseAuth

    /**
     * Tells the activity what to do on creation
     * Includes button press listeners and creating a Firebase instance
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //what to do when login button is pressed
        loginBtn.setOnClickListener {
            doLogin()
        }

        //what to do when sign-up button is pressed
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        //create instance of FirebaseAuth
        auth = FirebaseAuth.getInstance()
    }

    /**
     * Function to redirect the user to the MainActivity if logged in
     * Uses Toast to show updates
     * No parameter or return
     */
    fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            if(currentUser.isEmailVerified) {
                Toast.makeText(baseContext, "Authentication successful.",
                    Toast.LENGTH_SHORT).show()
                //startActivity(Intent(this, MainActivity::class.java))
                var isLoggedIn = true
                Intent(this, MainActivity::class.java).also {
                    it.putExtra("CURRENT_USER", isLoggedIn)
                    startActivity(it)
                }
                finish()
            }
            else {
                Toast.makeText(baseContext, "Please verify your email.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Function to log in a user using Firebase Authentication
     * Uses the above function to redirect the user to MainAcivity after logging in
     * Uses Toast to show updates
     * No parameter or return
     */
    private fun doLogin() {
        if(enterEmail.text.toString().isEmpty()) {
            enterEmail.error = "Please enter email"
            enterEmail.requestFocus()
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(enterEmail.text.toString()).matches()) {
            enterEmail.error = "Please enter valid email"
            enterEmail.requestFocus()
        }

        if(enterPassword.text.toString().isEmpty()) {
            enterEmail.error = "Please enter password"
            enterEmail.requestFocus()
        }

        else {
            auth.signInWithEmailAndPassword(
                enterEmail.text.toString(),
                enterPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                        Toast.makeText(
                            baseContext, "Login successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Login failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }


}