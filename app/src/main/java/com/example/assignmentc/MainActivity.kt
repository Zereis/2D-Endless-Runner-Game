package com.example.assignmentc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.assignmentc.HighScoreDatabase
import android.content.Context
import android.widget.TextView
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var highScoreDatabase: HighScoreDatabase
    private lateinit var highScoreTextView: TextView // Declare the TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highScoreDatabase = HighScoreDatabase.getDatabase(applicationContext)
        highScoreTextView = findViewById(R.id.textView4)

        displayHighestScore()

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val intent = Intent(this,GameActivity::class.java)
            startActivity(intent)

        }
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