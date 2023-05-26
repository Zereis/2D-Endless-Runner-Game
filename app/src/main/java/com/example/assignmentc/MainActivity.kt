package com.example.assignmentc

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var highScoreDatabase: HighScoreDatabase
    private lateinit var highScoreTextView: TextView // Declare the TextView
    private lateinit var youdiedTextView: TextView // Declare the TextView


    private val GAME_ACTIVITY_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highScoreDatabase = HighScoreDatabase.getDatabase(applicationContext)
        highScoreTextView = findViewById(R.id.textView4)
        youdiedTextView = findViewById(R.id.YouDiedTextView)

        displayHighestScore()

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val gameIntent = Intent(this,GameActivity::class.java)
            startActivityForResult(gameIntent, GAME_ACTIVITY_REQUEST_CODE)
            displayHighestScore()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GAME_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                youdiedTextView.visibility = View.VISIBLE
                displayHighestScore()
            }
            displayHighestScore()
        }
        displayHighestScore()
    }
    private fun displayHighestScore() {
        GlobalScope.launch(Dispatchers.Main) {
            val highestScore = highScoreDatabase.highScoreDao().getHighestScore()
            if (highestScore != null) {
                // Use the highestScore object in your game view UI
                highScoreTextView.text = "${highestScore.score}"
            } else {
                // Handle the case when no high score exists in the database
            }
        }
    }

}