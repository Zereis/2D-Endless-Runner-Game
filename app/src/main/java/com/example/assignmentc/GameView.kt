package com.example.assignmentc

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random
import android.os.CountDownTimer
import java.nio.file.Files.delete

class GameView(context: Context) : View(context), CoroutineScope by MainScope() {
    private var player: Player
    private var obstacle: Obstacle
    private var startPositionX: Float = 550f
    private var startPositionY: Float = 1800f

    private var initialTouchX = 0f // Initial touch position X-coordinate
    private var isLaneSwitching = false // Flag to track lane switch state

    private val INTERVAL = 2000L // 2 seconds

    private val SWIPE_THRESHOLD = 100

    private var leftLane: Int? = 1
    private var middleLane: Int? = 2
    private var rightLane: Int? = 3

    private var playerCurrentLane = 2


    var points: Int = 0

    // Mutable list of objects
    private var obstacleList = mutableListOf<Obstacle>()
    private val objectsToRemove = mutableListOf<Obstacle>()


    // Lane X coordinates for positioning.
    var leftLaneX: Int? = null
    var middleLaneX: Int? = null
    var rightLaneX: Int? = null

    // Y spawn position for objects
    private var obstacleRow: Int? = null
    private var playerRow: Int? = null

    var gameLoopJob: Job? = null

    val randomNumber = generateRandomNumber()
    var gameListener: GameListener? = null

    // Other game-related variables
    private val textPaint: Paint = Paint().apply {
        color = Color.WHITE
        textSize = 96f
    }
    interface GameListener {
        fun onCollisionDetected()
    }
    init {
        player = Player(context)
        obstacle = Obstacle(context)

        //Set up lanes, rows and start position for player and obstacles
        setLanes()
        setRows()
        setStartPos()

        obstacle.setPos(startPositionX, obstacleRow!!.toFloat())
        player.setPos(startPositionX, startPositionY)
        startTimer()
    }
    fun startTimer() {
        object : CountDownTimer(INTERVAL, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {

                // Code to be executed every interval (5 seconds in this case)
                // Replace this with your desired code

                //println("Timer tick")

            }
            override fun onFinish() {

                spawnObstacle()

                // Start the timer again for the next interval

                startTimer()
            }
        }.start()
    }
    fun generateRandomNumber(): Int {
        val random = Random()
        return random.nextInt(3) + 1
    }

    private fun checkCollision(playerX: Float, playerY: Float, objectX: Float, objectY: Float): Boolean {
        val distanceX = playerX - objectX
        val distanceY = playerY - objectY
        val distance = Math.sqrt((distanceX * distanceX + distanceY * distanceY).toDouble())

        // Check if the distance between the player and object is less than the sum of their radii
        return distance < player.playerCollsionRadius + obstacle.obstacleCollsionRadius
    }
    suspend fun gameLoop(): Runnable? {
        while (true) {
            // Perform game logic and rendering here
            updateGame()

            //Log.d("MESSAGE", "TEST")

            delay(16) // Delay for approximately 16 milliseconds (60 frames per second)
            invalidate() // Request a redraw of the view
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        gameLoopJob = launch {
            gameLoop()
        }
    }
    
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        gameLoopJob?.cancel()
    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        canvas?.drawColor(Color.RED)

        //Draw obstacles
        obstacleList.forEach{
            canvas?.drawCircle(it.posX, it.posY, it.obstacleCollsionRadius, it.paint!!)
        }

        //Draw player
        canvas?.drawCircle(player.posX, player.posY, player.playerCollsionRadius, player.paint!!)

        //Draw text
        canvas?.drawText("Score!", 50f, 150f, textPaint)
        canvas?.drawText(points.toString(), 150f, 300f, textPaint)
    }

    fun updateGame(){
        obstacleList.forEach {
            var oldY = it.posY
            var newY = oldY+it.speed
            it.setPos(it.posX,newY)

            if(checkCollision(player.posX, player.posY, it.posX, it.posY))
            {
                Log.d("Collision", "true")
                gameListener?.onCollisionDetected()
            }

            if (it.posY >= getScreenHeight() + it.obstacleCollsionRadius) {
                objectsToRemove.add(it)
            }

        }
        
        obstacleList.removeAll(objectsToRemove)
        println("Number of items: "+ obstacleList.count())
    }

    fun spawnObstacle(){
        obstacle = Obstacle(context)
        obstacle.posY = obstacleRow!!.toFloat()
        var lane: Int = generateRandomNumber()
        if(lane == 1)
            obstacle.posX = leftLaneX!!.toFloat()
        else if(lane == 2){
            obstacle.posX = middleLaneX!!.toFloat()
        }
        else if(lane == 3){
            obstacle.posX = rightLaneX!!.toFloat()
        }
        obstacleList.add(obstacle)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isLaneSwitching) {
                    initialTouchX = event.x
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isLaneSwitching) {
                    val currentTouchX = event.x
                    val distance = currentTouchX - initialTouchX

                    // Determine swipe direction
                    val swipeDirection = when {
                        distance > 0 -> "Right"
                        distance < 0 -> "Left"
                        else -> ""
                    }

                    // Switch lanes based on swipe direction
                    when (swipeDirection) {
                        "Right" -> {
                            if (playerCurrentLane < 3) {
                                // Switch to the next lane (increase player's position)
                                playerCurrentLane++
                                // Update player's position gradually for smooth movement

                                UpdatePlayerPos()
                                isLaneSwitching = true
                            }
                        }
                        "Left" -> {
                            if (playerCurrentLane > 1) {
                                // Switch to the previous lane (decrease player's position)
                                playerCurrentLane--
                                // Update player's position gradually for smooth movement

                                UpdatePlayerPos()
                                isLaneSwitching = true


                            }
                        }
                    }



                    // Reset initial touch position
                    initialTouchX = currentTouchX
                }
            }
            MotionEvent.ACTION_UP -> {
                // Reset the lane switch state when the finger is lifted
                isLaneSwitching = false
            }
        }
        invalidate()
        return true
    }

    private fun UpdatePlayerPos(){
        if(playerCurrentLane == 1){
            player.setPos(leftLaneX!!.toFloat(), startPositionY)
        }
        else if(playerCurrentLane == 2){
            player.setPos(middleLaneX!!.toFloat(), startPositionY)
        }
        else if(playerCurrentLane == 3){
            player.setPos(rightLaneX!!.toFloat(), startPositionY)
        }

        Log.d("Player Y Pos", player.posY.toString())
        Log.d("Player X Pos", player.posX.toString())
    }

    //Sets the lanes X position depending on screen size.
    private fun setLanes(){
        middleLaneX = getScreenWidth()/2
        leftLaneX = getScreenWidth()/4
        rightLaneX = getScreenWidth()/2 + getScreenWidth()/4
    }

    //Set the players and the obstacles rows (y) depending on screen size
    private fun setRows(){
        playerRow = getScreenHeight()-300
        obstacleRow = 0 //This should be something like -100 to spawn above the screen and then fall down
    }

    //Update start position of the player
    private fun setStartPos(){
        startPositionX = middleLaneX!!.toFloat()
        startPositionY = playerRow!!.toFloat()
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}

