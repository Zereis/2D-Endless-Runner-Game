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
import java.nio.file.Path

class Obstacle(private val context: Context) {

    var paint: Paint? = null
    var posX: Float = 0.0f
    var posY: Float = 0.0f
    var speed: Int = 10

    var obstacleCollsionRadius: Float = 50f

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