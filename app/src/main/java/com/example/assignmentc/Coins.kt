package com.example.assignmentc

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class Coins(private val context: Context) {
    var paint: Paint? = null
    var posX: Float = 0.0f
    var posY: Float = 0.0f
    var speed: Int = 10

    var coinsCollsionRadius: Float = 50f

    init {

        paint = Paint()
        paint!!.isFilterBitmap = true
        paint!!.isAntiAlias = true
        paint!!.color = Color.BLUE
    }

    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }
}