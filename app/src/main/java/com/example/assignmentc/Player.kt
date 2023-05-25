package com.example.assignmentc

import android.content.Context
import android.graphics.Color
import android.graphics.Paint


class Player(private val context: Context){

    var paint: Paint? = null
    var posX: Float = 0.0f
    var posY: Float = 0.0f
    var playerCollisionRadius: Float = 50f
    var score: Int = 0


    init {

        paint = Paint()
        paint!!.isFilterBitmap = true
        paint!!.isAntiAlias = true
        paint!!.color = Color.WHITE

    }

    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }

    fun update() {

    }

    fun moveLeft() {

    }

    fun moveRight() {

    }




}