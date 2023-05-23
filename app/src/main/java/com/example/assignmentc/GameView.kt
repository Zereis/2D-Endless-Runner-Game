package com.example.assignmentc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View

class GameView(context: Context) : View(context) {

    private var player: Player
    var startPositionX: Float = 550f
    var startPositionY: Float = 1800f
    private val SWIPE_THRESHOLD = 100
    var test: Boolean = false
    // Other game-related variables

    // KEVIN COMMENT

    init {
    player = Player(context)


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
}

