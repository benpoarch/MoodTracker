package com.example.moodtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moodtracker.login.LoginActivity
import com.example.moodtracker.mood_test.MoodTestActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mood_test.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //go to login screen
        GoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        //go to mood test
        MoodTestBtn.setOnClickListener {
            startActivity(Intent(this, MoodTestActivity::class.java))
            finish()
        }

        //go to emergency contacts
        EmergencyBtn.setOnClickListener {
            startActivity(Intent(this,EmergencyContactActivity::class.java))
            finish()
        }




    }



    //for login activity
    private fun logout() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Successfully logged out.",
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(baseContext, "Not logged in.",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun login() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            Toast.makeText(baseContext, "You are already logged in.",
                Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
}