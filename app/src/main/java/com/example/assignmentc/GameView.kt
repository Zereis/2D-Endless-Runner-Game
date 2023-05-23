package com.example.assignmentc

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class GameView(context: Context) : View(context) {
    // Objects
    private var player: Player
    private var obstacle: Obstacle

    // Player start position
    private var startPositionX: Float = 550f
    private var startPositionY: Float = 1800f

    private val SWIPE_THRESHOLD = 100
    var test: Boolean = false


    // Lane X coordinates for positioning.
    private var leftLaneX: Int? = 1
    private var middleLaneX: Int? = 2
    private var rightLaneX: Int? = 3

    // Y spawn position for objects
    private var spawnY: Int? = null

    // Other game-related variables

    init {
        player = Player(context)
        obstacle = Obstacle(context)

        setUpLanes()

        startPositionX = middleLaneX!!.toFloat()
        spawnY = 0
    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)

        if(test)
        {
            //Player
            canvas?.drawCircle(startPositionX,startPositionY, 50f, player.paint!!)
            //Obstacle
            canvas?.drawCircle(startPositionX,100f,50f, obstacle.paint!!)
        }
        else
        {
            //Player
            canvas?.drawCircle(startPositionX,startPositionY, 50f, player.paint!!)
            //Obstacle
            canvas?.drawCircle(startPositionX,spawnY!!.toFloat(),50f, obstacle.paint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Record the initial touch position
                player.circleX = event.x
                true
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.x - player.circleX
                if (deltaX < SWIPE_THRESHOLD) {
                    // Swiped left
                    test = true
                }
                else if (deltaX > SWIPE_THRESHOLD) {
                    // Swiped left
                    test = true
                }
                true
            }
            else -> false
        }
        invalidate()
        return true
    }

    //Sets the lanes X position depending on screen size.
    private fun setUpLanes(){
        middleLaneX = getScreenWidth()/2
        leftLaneX = getScreenWidth()/4
        rightLaneX = getScreenWidth()/2 + getScreenWidth()/4
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}

