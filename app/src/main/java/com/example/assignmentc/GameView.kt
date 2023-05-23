package com.example.assignmentc

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class GameView(context: Context) : View(context) {


    var paint: Paint? = null
    var circleX: Float = 0.0f
    var circleY: Float = 0.0f

    // Lane X coordinates for positioning.
    var leftLaneX: Int? = null
    var middleLaneX: Int? = null
    var rightLaneX: Int? = null

    // Y spawn position for objects
    var spawnY: Int? = null

    // Other game-related variables

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

    fun setUpLanes(){
        middleLaneX = getScreenWidth()/4
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

