package com.example.assignmentc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF

class Obstacle(private val context: Context) {

    var paint: Paint? = null
    var posX: Float = 0.0f
    var posY: Float = 0.0f

    var position: PointF =
        PointF(0f, 0f)

    var speed: PointF = PointF(0f, 0f)

    init {

        paint = Paint()
        paint!!.isFilterBitmap = true
        paint!!.isAntiAlias = true
        paint!!.color = Color.BLACK
    }

    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }
}