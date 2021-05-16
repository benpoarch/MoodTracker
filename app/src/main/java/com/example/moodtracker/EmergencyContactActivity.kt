package com.example.moodtracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.login.LoginActivity
import kotlinx.android.synthetic.main.activity_emergency_contact.*

class EmergencyContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact)

        MindBtn.setOnClickListener {
            val url = "https://www.mind.org.uk/information-support/guides-to-support-and-services/crisis-services/helplines-listening-services/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }



    }
}