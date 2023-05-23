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


    var paint: Paint? = null
    var circleX: Float = 0.0f
    var circleY: Float = 0.0f

    // Other game-related variables

    // KEVIN COMMENT

    init {

        paint = Paint()
        paint!!.isFilterBitmap = true
        paint!!.isAntiAlias = true
        paint!!.color = Color.YELLOW
        circleX = 100.0f
        circleY = 100.0f

    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)
        canvas?.drawCircle(circleX,circleY, 50f, paint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Handle touch events and update player controls

        circleX = event!!.x
        circleY = event!!.y
        invalidate()
        return true
    }
}

