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


class Player(private val context: Context){

    var paint: Paint? = null
    var circleX: Float = 0.0f
    var circleY: Float = 0.0f
    var radius: Float = 50f


    init {

        paint = Paint()
        paint!!.isFilterBitmap = true
        paint!!.isAntiAlias = true
        paint!!.color = Color.YELLOW
        circleX = 100.0f
        circleY = 100.0f

    }

    fun update() {

    }

    fun moveLeft() {

    }

    fun moveRight() {

    }




}