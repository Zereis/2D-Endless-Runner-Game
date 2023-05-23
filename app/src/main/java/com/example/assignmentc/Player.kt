package com.example.assignmentc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF


class Player(private val context: Context, private val screenX: Int, private val screenY: Int){
    private val shipWidth = 100
    private val shipHeight = 100
    private val initialPositionX = screenX / 2f
    private val initialPositionY = screenY - shipHeight.toFloat()

    private val shipRect: RectF = RectF()
    var image: Bitmap

    var positionX = initialPositionX
    var positionY = initialPositionY

    var speed = 10f

    init {
        shipRect.set(
            initialPositionX - shipWidth / 2f,
            initialPositionY - shipHeight / 2f,
            initialPositionX + shipWidth / 2f,
            initialPositionY + shipHeight / 2f
        )
        // Load the spaceship image from the drawable resource
        val resourceId = context.resources.getIdentifier("rocket3", "drawable", context.packageName)
        image = BitmapFactory.decodeResource(context.resources, resourceId)
    }

    fun draw(canvas: Canvas) {
        // Calculate the left, top, right, and bottom coordinates of the spaceship's image
        val left = positionX - image.width / 2
        val top = positionY - image.height / 2
        val right = positionX + image.width / 2
        val bottom = positionY + image.height / 2

        // Draw the spaceship's image on the canvas
        canvas.drawBitmap(image, null, Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt()), null)
    }

    fun update() {
        // Update the ship's position based on its speed
        positionY -= speed

        // Update the ship's RectF
        shipRect.top = positionY - shipHeight / 2f
        shipRect.bottom = positionY + shipHeight / 2f
    }

    fun moveLeft() {
        // Move the ship to the left
        positionX -= speed
        if (positionX < 0) {
            positionX = 0f
        }
    }

    fun moveRight() {
        // Move the ship to the right
        positionX += speed
        if (positionX + shipWidth > screenX) {
            positionX = (screenX - shipWidth).toFloat()
        }
    }

    fun getCollisionRect(): RectF {
        return shipRect
    }

    fun resetPosition() {
        positionX = initialPositionX
        positionY = initialPositionY
        shipRect.top = positionY - shipHeight / 2f
        shipRect.bottom = positionY + shipHeight / 2f
    }
}