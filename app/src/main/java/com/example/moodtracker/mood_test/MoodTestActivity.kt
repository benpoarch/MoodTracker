package com.example.moodtracker.mood_test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R
import kotlinx.android.synthetic.main.activity_mood_test.*

class MoodTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_test)

        //takes user back to main menu
        BackBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //finalises mood test and takes user back to menu
        DoneBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

            //TODO: add up score and save to calendar


        }


    }



}