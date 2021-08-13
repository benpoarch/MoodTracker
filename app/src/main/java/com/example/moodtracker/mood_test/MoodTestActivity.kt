package com.example.moodtracker.mood_test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.activity_mood_test.*
import java.security.KeyStore
import kotlinx.android.parcel.Parcelize

/**
 * Activity to display Beck's Depression Inventory and calculate score
 * @author Ben Poarch
 */
class MoodTestActivity : AppCompatActivity() {

    /**
     * Tells the activity what to do on creation
     * Runs the function to calculate the mood score
     * Sets the button to send the score back to the main activity via intent
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_test)

        //calls method and returns score
        analyseSpinners()

        //takes user back to main menu
        BackBtn.setOnClickListener {
            var score = this.analyseSpinners() //calculates score from all of spinners

            //sends new score back to MainActivity
            Intent(this, MainActivity::class.java).also {
                it.putExtra("NEW_SCORE", score)
                startActivity(it)
            }
            finish()
        }


    }

    /**
     * Adds up all of the values taken in by the spinners in the mood test
     * @return the integer total which is the new mood score
     */
    fun analyseSpinners(): Int {
        var total = spinner1.getSelectedItem().toString().toInt() + spinner2.getSelectedItem()
            .toString().toInt() + spinner3.getSelectedItem().toString().toInt() + spinner4
            .getSelectedItem().toString().toInt() + spinner5.getSelectedItem().toString().toInt() +
                spinner6.getSelectedItem().toString().toInt()+ spinner7.getSelectedItem().toString().toInt() +
                spinner8.getSelectedItem().toString().toInt() + spinner9.getSelectedItem().toString().toInt() +
                spinner10.getSelectedItem().toString().toInt() + spinner11.getSelectedItem().toString().toInt() +
                spinner12.getSelectedItem().toString().toInt() + spinner13.getSelectedItem().toString().toInt() +
                spinner14.getSelectedItem().toString().toInt()+ spinner15.getSelectedItem().toString().toInt() +
                spinner16.getSelectedItem().toString().toInt() + spinner17.getSelectedItem().toString().toInt() +
                spinner18.getSelectedItem().toString().toInt() + spinner19.getSelectedItem().toString().toInt() +
                spinner20.getSelectedItem().toString().toInt() + spinner21.getSelectedItem().toString().toInt()
        return total
    }

}