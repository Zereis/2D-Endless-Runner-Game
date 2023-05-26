package com.example.assignmentc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.util.Log

class Player(private val context: Context){

    private var mediaPlayer: MediaPlayer? = null
    private var coinCollect: MediaPlayer? = null
    private var switchLaneSound: MediaPlayer? = null
    private var backgroundSound: MediaPlayer? = null
    private var backgroundSound2: MediaPlayer? = null
    private var currentbackgroundSound: MediaPlayer? = null
    var posX: Float = 0.0f
    var posY: Float = 0.0f

    var score: Int = 0
    var playerBitmap: Bitmap? = null
        private set // Make the playerBitmap property private to the outside

    private var width: Int = 0
    private var height: Int = 0
    var playerCollisionRadius: Float = 0f

    var speed: Float = 0f

    private var isPlaying: Boolean = false
    private var isPlayingCoins: Boolean = false
    private var isPlayingSwitch: Boolean = false

    init {
        mediaPlayer = MediaPlayer.create(context, R.raw.carcrash)
        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            mediaPlayer?.release()
            mediaPlayer = null
        }

        coinCollect = MediaPlayer.create(context, R.raw.coincollect)
        coinCollect?.setOnCompletionListener {
            isPlayingCoins = false
            coinCollect?.release()
            coinCollect = MediaPlayer.create(context, R.raw.coincollect)
        }
        switchLaneSound = MediaPlayer.create(context, R.raw.carskid)
        switchLaneSound?.setOnCompletionListener {
            isPlayingSwitch = false

            switchLaneSound = MediaPlayer.create(context, R.raw.carskid)
        }
        backgroundSound = MediaPlayer.create(context, R.raw.carengine)
        backgroundSound2 = MediaPlayer.create(context, R.raw.carengine)
        currentbackgroundSound = MediaPlayer.create(context, R.raw.carengine)

        backgroundSound?.isLooping = true
        backgroundSound2?.isLooping = true

        currentbackgroundSound = backgroundSound
        currentbackgroundSound?.start()

        currentbackgroundSound?.setOnCompletionListener {
            if ( currentbackgroundSound == backgroundSound) {
                currentbackgroundSound =      backgroundSound2
                backgroundSound?.reset()
            } else {
                currentbackgroundSound = backgroundSound
                backgroundSound2?.reset()
            }
            currentbackgroundSound ?.start()
        }


        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player_car)

        width = 200//originalBitmap.width // Desired width of the player image
        height = 400//originalBitmap.height // Desired height of the player image

        playerCollisionRadius = (height/2).toFloat()
        
        playerBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false)
        Log.d("PlayerImage", "Image width: $width, height: $height")

        speed = 50f

    }
    fun setPos(X: Float, Y: Float){
        posX = X
        posY = Y
    }
    fun collisionPlaySound()
    {
        if (mediaPlayer?.isPlaying != true) {
            mediaPlayer?.start()
        }
    }
    fun collectPlaySound()
    {
        if(coinCollect?.isPlaying != true)
        coinCollect?.start()
    }
    fun switchingLanePlaySound()
    {
        switchLaneSound?.start()
    }
    fun destroy()
    {
        width = 0
        height = 0
        playerCollisionRadius = 0f
        mediaPlayer = null
        backgroundSound?.release()
        backgroundSound2?.release()
        backgroundSound = null
        backgroundSound2 = null

    }
}