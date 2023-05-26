package com.example.assignmentc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.util.Log

class Coins(private val context: Context) {

    var coinsBitmap: Bitmap? = null
        private set // Make the playerBitmap property private to the outside
    var posX: Float? = 0.0f
    var posY: Float? = 0.0f
    var speed: Int? = 10
    var coinCollisionRadius: Float? = 50f
    private var width: Int = 0
    private var height: Int = 0

    init {
        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin)
        width = 100 //originalBitmap.width // Desired width of the player image
        height = 100 // originalBitmap.heightDesired height of the player image
        coinsBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false)

    }

    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }

    fun destroy(){

        posX = null
        posY = null
        speed = null
        coinCollisionRadius = null
    }

}