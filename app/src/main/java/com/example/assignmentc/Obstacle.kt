package com.example.assignmentc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint

class Obstacle(private val context: Context) {


    var posX: Float? = 0.0f
    var posY: Float? = 0.0f
    var speed: Int? = 10
    var obstacleCollisionRadius: Float? = 0f

    var obstacleBitmap: Bitmap? = null
        private set // Make the playerBitmap property private to the outside
    private var width: Int? = 0
    private var height: Int? = 0

    init {
        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.oil_barrel)
        width = 150 //originalBitmap.width // Desired width of the player image
        height = 175 // originalBitmap.heightDesired height of the player image

        obstacleCollisionRadius = (height!! /2).toFloat()

        obstacleBitmap = Bitmap.createScaledBitmap(originalBitmap, width!!, height!!, false)
    }

    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }

    fun destroy(){
        posX = null
        posY = null
        speed = null
        obstacleCollisionRadius = null
        width = null
        height = null
        obstacleBitmap = null
    }
}