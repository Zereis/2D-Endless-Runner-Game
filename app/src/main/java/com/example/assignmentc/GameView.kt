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

    private val SWIPE_THRESHOLD = 100
    var test: Boolean = false


    // Lane X coordinates for positioning.
    var leftLaneX: Int? = 1
    var middleLaneX: Int? = 2
    var rightLaneX: Int? = 3

    // Y spawn position for objects
    var spawnY: Int? = null


    // Other game-related variables

    init {
        player = Player(context)

        setUpLanes()
        startPositionX = rightLaneX!!.toFloat()



    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)


        if(test)
        {
            canvas?.drawCircle(200f,startPositionY, 50f, player.paint!!)
        }
        else
        {
            canvas?.drawCircle(startPositionX,startPositionY, 50f, player.paint!!)
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
    fun setUpLanes(){
        middleLaneX = getScreenWidth()/2
        leftLaneX = getScreenWidth()/4
        rightLaneX = getScreenWidth()/2 + getScreenWidth()/4
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}

