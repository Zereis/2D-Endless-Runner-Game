package com.example.assignmentc

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class GameView(context: Context) : View(context) {
    private var player: Player
    private var obstacle: Obstacle
    private var startPositionX: Float = 550f
    private var startPositionY: Float = 1800f

    private var initialTouchX = 0f // Initial touch position X-coordinate
    private var isLaneSwitching = false // Flag to track lane switch state

    private val SWIPE_THRESHOLD = 100

    private var leftLane: Int? = 1
    private var middleLane: Int? = 2
    private var rightLane: Int? = 3

    private var playerCurrentLane = 2

    // Lane X coordinates for positioning.
    var leftLaneX: Int? = null
    var middleLaneX: Int? = null
    var rightLaneX: Int? = null

    // Y spawn position for objects
    private var obstacleRow: Int? = null
    private var playerRow: Int? = null

    private var gameStarted: Boolean = false

    private val frameRate = 16 // 1000 milliseconds / 60 frames ≈ 16 milliseconds per frame

    // Other game-related variables

    init {
        player = Player(context)
        obstacle = Obstacle(context)

        //Set up lanes, rows and start position for player and obstacles
        setLanes()
        setRows()
        setStartPos()

        obstacle.setPos(startPositionX, obstacleRow!!.toFloat())

        startGameLoop()
    }

    private val gameLoopRunnable = object : Runnable {
        override fun run() {
            if (gameStarted) {
                // Game update logic
                updateGame()
                invalidate()

                /*
                Log.d("Y", obstacle.posY.toString())
                Log.d("X", obstacle.posX.toString())

                 */
                Log.d("MESSAGE", "TESTESTESTEST.................ESETESETSE")

                // Schedule the next iteration of the game loop
                postDelayed(this, frameRate.toLong())
            }
        }
    }

    // Method to start the game loop
    fun startGameLoop() {
        gameStarted = true
        post(gameLoopRunnable)
    }

    // Method to stop the game loop
    fun stopGameLoop() {
        gameStarted = false
        removeCallbacks(gameLoopRunnable)
    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)

        canvas?.drawCircle(obstacle.posX, obstacle.posY, 50f, obstacle.paint!!)

        if(playerCurrentLane == leftLane)
        {
            canvas?.drawCircle(leftLaneX!!.toFloat(),startPositionY, 50f, player.paint!!)
        }
        else if(playerCurrentLane == middleLane)
        {
            canvas?.drawCircle(middleLaneX!!.toFloat(),startPositionY, 50f, player.paint!!)
        }
        else if(playerCurrentLane == rightLane)
        {
            canvas?.drawCircle(rightLaneX!!.toFloat(),startPositionY, 50f, player.paint!!)
        }
        invalidate()
    }

    fun updateGame(){
        // Game logic
        var oldY = obstacle.posY
        var newY = oldY+1
        obstacle.setPos(startPositionX,newY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isLaneSwitching) {
                    initialTouchX = event.x
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isLaneSwitching) {
                    val currentTouchX = event.x
                    val distance = currentTouchX - initialTouchX

                    // Determine swipe direction
                    val swipeDirection = when {
                        distance > 0 -> "Right"
                        distance < 0 -> "Left"
                        else -> ""
                    }

                    // Switch lanes based on swipe direction
                    when (swipeDirection) {
                        "Right" -> {
                            if (playerCurrentLane < 3) {
                                // Switch to the next lane (increase player's position)
                                playerCurrentLane++
                                // Update player's position gradually for smooth movement
                                isLaneSwitching = true
                            }
                        }
                        "Left" -> {
                            if (playerCurrentLane > 1) {
                                // Switch to the previous lane (decrease player's position)
                                playerCurrentLane--
                                // Update player's position gradually for smooth movement
                                isLaneSwitching = true
                            }
                        }
                    }

                    // Reset initial touch position
                    initialTouchX = currentTouchX
                }
            }
            MotionEvent.ACTION_UP -> {
                // Reset the lane switch state when the finger is lifted
                isLaneSwitching = false
            }
        }
        invalidate()
        return true
    }

    //Sets the lanes X position depending on screen size.
    private fun setLanes(){
        middleLaneX = getScreenWidth()/2
        leftLaneX = getScreenWidth()/4
        rightLaneX = getScreenWidth()/2 + getScreenWidth()/4
    }

    //Set the players and the obstacles rows (y) depending on screen size
    private fun setRows(){
        playerRow = getScreenHeight()-300
        obstacleRow = 0 //This should be something like -100 to spawn above the screen and then fall down
    }

    //Update start position of the player
    private fun setStartPos(){
        startPositionX = middleLaneX!!.toFloat()
        startPositionY = playerRow!!.toFloat()
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}

