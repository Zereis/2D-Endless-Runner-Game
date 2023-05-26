package com.example.assignmentc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log

class Player(private val context: Context){

    var posX: Float = 0.0f
    var posY: Float = 0.0f

    var score: Int = 0
    var playerBitmap: Bitmap? = null
        private set // Make the playerBitmap property private to the outside

    private var width: Int = 0
    private var height: Int = 0
    var playerCollisionRadius: Float = 0f
    init {

        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player_car)

        width = 200//originalBitmap.width // Desired width of the player image
        height = 400//originalBitmap.height // Desired height of the player image

        playerCollisionRadius = (height/2).toFloat()
        
        playerBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false)
        Log.d("PlayerImage", "Image width: $width, height: $height")
    }
    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }
}