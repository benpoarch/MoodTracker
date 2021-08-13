package com.example.moodtracker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moodtracker.login.LoginActivity
import com.example.moodtracker.mood_test.MoodTestActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity to display how to use the application and how the score is evaluated
 * @author Ben Poarch
 */
class InfoActivity : AppCompatActivity() {

    /**
     * Tells the activity what to do on creation
     * Adds app bar to activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        bottomNavigationView.background = null
        createAppBar()
    }


    /**
     * Function to differentiate the different actions taken when each separate item on the app
     * bar is pressed.
     * @return boolean true if a button is pressed and false if not
     */
    private fun createAppBar() {
        bottomNavigationView.menu.getItem(2).isEnabled = false

        //check if user is logged in
        var isLoggedIn = intent.getBooleanExtra("CURRENT_USER", false)

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    if (isLoggedIn) logout()
                    if (!isLoggedIn) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.help -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.sos -> {
                    //startActivity(Intent(this, EmergencyContactActivity::class.java))
                    val url = "https://www.mind.org.uk/information-support/guides-to-support-and-services/crisis-services/helplines-listening-services/"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.add -> {
                    startActivity(Intent(this, MoodTestActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        newMoodTestBtn.setOnClickListener() {
            startActivity(Intent(this, MoodTestActivity::class.java))
        }
    }

    /**
     * Logs the user out using Firebase
     */
    private fun logout() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(baseContext, "Successfully logged out.",
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(baseContext, "Not logged in.",
                Toast.LENGTH_SHORT).show()
        }
    }


}

