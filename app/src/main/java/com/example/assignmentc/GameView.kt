package com.example.assignmentc

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class GameView(context: Context) : View(context) {

    private var player: Player
    var startPositionX: Float = 550f
    var startPositionY: Float = 1800f

    var initialTouchX = 0f // Initial touch position X-coordinate
    var isLaneSwitching = false // Flag to track lane switch state

    private val SWIPE_THRESHOLD = 100


    var leftLane: Int? = 1
    var middleLane: Int? = 2
    var rightLane: Int? = 3

    var playerCurrentLane = 2

    // Lane X coordinates for positioning.
    //var leftLaneX: Int? = null
    //var middleLaneX: Int? = null
    //var rightLaneX: Int? = null

    // Y spawn position for objects
    var spawnY: Int? = null


    // Other game-related variables

    init {
        player = Player(context)

        //setUpLanes()
        //startPositionX = rightLaneX!!.toFloat()



    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)


        if(playerCurrentLane == leftLane)
        {
            canvas?.drawCircle(200f,startPositionY, 50f, player.paint!!)
        }
        else if(playerCurrentLane == middleLane)
        {
            canvas?.drawCircle(550f,startPositionY, 50f, player.paint!!)
        }
        else if(playerCurrentLane == rightLane)
        {
            canvas?.drawCircle(800f,startPositionY, 50f, player.paint!!)
        }

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
    fun setUpLanes(){
        //middleLaneX = getScreenWidth()/2
        //leftLaneX = getScreenWidth()/4
        //rightLaneX = getScreenWidth()/2 + getScreenWidth()/4
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}

