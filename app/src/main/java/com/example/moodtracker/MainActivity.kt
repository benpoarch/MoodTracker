package com.example.moodtracker

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.BLUE
import android.hardware.camera2.params.RggbChannelVector.BLUE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.moodtracker.login.LoginActivity
import com.example.moodtracker.mood_test.MoodTestActivity
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mood_test.*
import java.util.*
import ru.cleverpumpkin.calendar.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.collections.ArrayList
import android.graphics.Color.BLUE
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcel
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.moodtracker.login.SignUpActivity
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Activity to display the mood graph
 * @author Ben Poarch
 */
class MainActivity : AppCompatActivity() {

    var count = 0f //to add each new score next to eachother rather than above/below

    //initialisers for line chart
    lateinit var linelist:ArrayList<Entry>
    lateinit var lineDataSet: LineDataSet
    lateinit var lineData: LineData

    /**
     * Tells the activity what to do on creation
     * Adds app bar to activity
     * Takes in new score from MoodTestActivity and adds to graph
     * Starts graph
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add app bar to activity
        bottomNavigationView.background = null
        createAppBar()

        //initialise line chart
        linelist = ArrayList()

        //get new score from MoodTestActivity
        val score = intent.getIntExtra("NEW_SCORE",64)

        //add score labels to top of UI and add to chart
        setNewScore(score)

        //create chartn on activity
        startChart()

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
            when (item.itemId) {    //id of each item within the app bar
                R.id.home -> {  //go to main activity
                    startActivity(Intent(this, MainActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {   //go to login activity
                    if (isLoggedIn) logout()
                    if (!isLoggedIn) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.help -> {  //go to info activity
                    startActivity(Intent(this, InfoActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.sos -> {   //go to emergency contact web page
                    val url = "https://www.mind.org.uk/information-support/guides-to-support-and-services/crisis-services/helplines-listening-services/"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.add -> {   //go to mood test activity
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
     * Sets new score to the text labels with the correct evaluation
     * Adds new score to the Arraylist that is displayed on the graph
     * Increments the counter so that each new entry is on a new line in x axis
     */
    private fun setNewScore(score: Int) {
        //if the score is valid change the text label to show the latest score
        if (score<64) { //is the score valid
            latestScore.text = "Recent score: $score"
            evaluation.text = interpretScore(score)
            if (score!=0) linelist.add(Entry(count,score.toFloat()))    //add to graph

        }
        count++ //increment the x axis position ready for the next entry
    }

    /**
     * Decides on the differnt factors for the line graph
     * Including the labels, colours and font sizes
     */
    private fun startChart() {
        lineDataSet = LineDataSet(linelist, "Mood Score")
        lineData = LineData(lineDataSet)
        line_chart.data=lineData

        line_chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        line_chart.axisRight.textColor = Color.WHITE
        line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        line_chart.xAxis.textColor = Color.WHITE
        line_chart.description.text = "Mood Chart"

        lineDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        lineDataSet.valueTextColor = Color.GREEN
        lineDataSet.valueTextSize = 15f
        lineDataSet.setDrawFilled(true)
        lineDataSet.setColors(Color.GREEN)
    }

    /**
     * Interprets the score depending on it's corresponding string
     * Uses Beck's Depression Inventory
     * @param the new score to be interpreted as an integer
     * @return the string evaluation
     */
    private fun interpretScore(score: Int): String {
        var eval = when(score) {
            0 -> "Invalid score - not added to chart"
            in 1..10 -> "Considered normal"
            in 11..16 -> "Mild mood disturbance"
            in 17..20 -> "Borderline clinical depression"
            in 21..30 -> "Moderate depression"
            in 31..40 -> "Severe depression"
            else -> "Extreme depression"
        }
        return eval
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
