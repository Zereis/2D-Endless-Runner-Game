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

<<<<<<< Updated upstream
=======
    private var player: Player
    var startPositionX: Float = 550f
    var startPositionY: Float = 1800f
    var playerCurrentLane: Int = 2

    var leftLane: Int = 1
    var middleLane: Int = 2
    var rightLane: Int = 3

    private val SWIPE_THRESHOLD = 100
    var test: Boolean = false


    // Lane X coordinates for positioning.
    var leftLaneX: Int? = null
    var middleLaneX: Int? = null
    var rightLaneX: Int? = null

    // Y spawn position for objects
    var spawnY: Int? = null
>>>>>>> Stashed changes

    var paint: Paint? = null
    var circleX: Float = 0.0f
    var circleY: Float = 0.0f

    // Other game-related variables

    // KEVIN COMMENT

    init {
<<<<<<< Updated upstream

        paint = Paint()
        paint!!.isFilterBitmap = true
        paint!!.isAntiAlias = true
        paint!!.color = Color.YELLOW
        circleX = 100.0f
        circleY = 100.0f
=======
        player = Player(context)

        setUpLanes()
        startPositionX = rightLaneX!!.toFloat()
>>>>>>> Stashed changes

    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)
<<<<<<< Updated upstream
        canvas?.drawCircle(circleX,circleY, 50f, paint!!)
=======


        if(playerCurrentLane == 2)
        {
            canvas?.drawCircle(200f,startPositionY, 50f, player.paint!!)
        }
        else if (playerCurrentLane == 1)
        {
            canvas?.drawCircle(startPositionX,startPositionY, 50f, player.paint!!)
        }
        else if (playerCurrentLane == 3)
        {
            canvas?.drawCircle(startPositionX,startPositionY, 50f, player.paint!!)
        }
>>>>>>> Stashed changes
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Handle touch events and update player controls

        circleX = event!!.x
        circleY = event!!.y
        invalidate()
        return true
    }
}

