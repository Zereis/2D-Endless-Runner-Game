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

    // Other game-related variables

    // KEVIN COMMENT

    init {
    player = Player(context)


    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)
        canvas?.drawCircle(startPositionX,startPositionY, 50f, player.paint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Handle touch events and update player controls

        player.circleX = event.x
        player.circleY = event.y
        invalidate()
        return true
    }
}

