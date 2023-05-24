package com.example.assignmentc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameActivity : AppCompatActivity(), GameView.GameListener {

    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create an instance of GameView
        gameView = GameView(this)

        // Set the gameListener property to this activity
        gameView.gameListener = this

        // Set the GameView as the content view of the activity
        setContentView(gameView)


    }
    override fun onCollisionDetected() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the GameActivity
    }
}