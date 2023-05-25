package com.example.assignmentc

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random
import android.os.CountDownTimer
import com.example.assignmentc.database.HighScore

class GameView(context: Context) : View(context), CoroutineScope by MainScope() {
    // Objects
    private var player: Player
    private var obstacle: Obstacle
    private var coins: Coins

    // Start position with default values
    private var startPositionX: Float = 550f
    private var startPositionY: Float = 1800f

    // Variables for swiping and switching lanes
    private var initialTouchX = 0f // Initial touch position X-coordinate
    private var isLaneSwitching = false // Flag to track lane switch state

    // Interval for timer
    private var timeInterval = 2000L // 2 seconds

    // Discriminator for lanes (NOT USED?)
    private var leftLane: Int? = 1
    private var middleLane: Int? = 2
    private var rightLane: Int? = 3

    // Current lane for player and default value
    private var playerCurrentLane = 2

    // Variables for the score
    var points: Int = 0
    //val highScoreRepository = HighScoreRepository(context)

    // Mutable list of objects
    private var obstacleList = mutableListOf<Obstacle>()
    private val obstaclesToRemove = mutableListOf<Obstacle>()
    private var coinsList = mutableListOf<Coins>()
    private val coinsToRemove = mutableListOf<Coins>()

    // Lane X coordinates for positioning.
    var leftLaneX: Int? = null
    var middleLaneX: Int? = null
    var rightLaneX: Int? = null

    // Y spawn position for objects
    //private var coinsRow: Int? = null
    private var objectRow: Int? = null
    private var playerRow: Int? = null
    private var gameSpeed: Int? = null

    private var speedCounter: Int = 0

    // Variable for coroutine
    var gameLoopJob: Job? = null

    // Random number generator between 1-3
    val randomNumber = generateRandomNumber()

    // Listener for when the player loses game
    var gameListener: GameListener? = null

    var timerStarted: Boolean = false

    // Other game-related variables
    init {
        // Initialize up objects
        player = Player(context)
        obstacle = Obstacle(context)
        coins = Coins(context)

        // Set up lanes, rows and start position for player and obstacles
        setLanes()
        setRows()
        setStartPos()

        val highScore = HighScore(score = 100)

        gameSpeed = 10
        //updateSpeed()
        speedCounter = 0

        coins.setPos(startPositionX, objectRow!!.toFloat())
        obstacle.setPos(startPositionX, objectRow!!.toFloat())
        player.setPos(startPositionX, startPositionY)
        timerStarted = true
        startTimer()
    }

    private val textPaint: Paint = Paint().apply {
        color = Color.WHITE
        textSize = 96f
    }

    interface GameListener {
        fun onCollisionDetected()
    }

    fun startTimer() {
        object : CountDownTimer(timeInterval, 1000L) {
            override fun onTick(millisUntilFinished: Long) {

                println("Timer tick")
            }
            override fun onFinish() {
                var tempNumber = generateRandomObject()
                if(tempNumber>=50){
                    spawnCoin()
                }
                else{
                    spawnObstacle()
                }

                speedCounter++
                if(speedCounter>=7){
                    updateSpeed()
                    speedCounter = 0
                }

                println("Timer finish")
                // Start the timer again for the next interval
                if(timerStarted){
                    startTimer()
                }
            }
        }.start()
    }

    fun generateRandomNumber(): Int {
        val random = Random()
        return random.nextInt(3) + 1
    }
    fun generateRandomObject(): Int {
        val random = Random()
        return random.nextInt(100)+1
    }

    private fun checkCollision(playerX: Float, playerY: Float, objectX: Float, objectY: Float, objectRadius: Float): Boolean {
        val distanceX = playerX - objectX
        val distanceY = playerY - objectY
        val distance = Math.sqrt((distanceX * distanceX + distanceY * distanceY).toDouble())

        // Check if the distance between the player and object is less than the sum of their radii
        return distance < player.playerCollisionRadius!! + objectRadius!!
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
            canvas?.drawCircle(it.posX!!, it.posY!!, it.obstacleCollisionRadius!!, it.paint!!)
        }

        coinsList.forEach{
            canvas?.drawCircle(it.posX!!, it.posY!!, it.coinCollisionRadius!!, it.paint!!)
        }
        //Draw player
        canvas?.drawCircle(player.posX, player.posY, player.playerCollisionRadius, player.paint!!)

        //Draw text
        canvas?.drawText("Score!", 50f, 150f, textPaint)
        canvas?.drawText(player.score.toString(), 150f, 300f, textPaint)
    }

    fun updateGame() {
        obstacleList.forEach {
            val oldY = it.posY
            val newY = oldY?.plus(it.speed!!)
            it.setPos(it.posX!!, newY!!)

            if (checkCollision(player.posX, player.posY, it.posX!!, it.posY!!, it.obstacleCollisionRadius!!))
            {
                obstaclesToRemove.add(it)
                timerStarted = false
                gameListener?.onCollisionDetected()
            }

            if (it.posY!! >= getScreenHeight() + it.obstacleCollisionRadius!!) {
                obstaclesToRemove.add(it)
            }
        }

        coinsList.forEach {
            val oldY = it.posY
            val newY = oldY?.plus(it.speed!!)
            it.setPos(it.posX!!, newY!!)

            // player collide with coins
            if (checkCollision(player.posX, player.posY, it.posX!!, it.posY!!, it.coinCollisionRadius!!))
            {
                player.score++
                coinsToRemove.add(it)
            }

            if (it.posY!! >= getScreenHeight() + it.coinCollisionRadius!!) {
                coinsToRemove.add(it)
            }
        }

        obstacleList.removeAll(obstaclesToRemove)
        obstaclesToRemove.forEach {
            it.destroy()
        }
        coinsList.removeAll(coinsToRemove)
        coinsToRemove.forEach{
            it.destroy()
        }

        //println("Number of obstacles: ${obstacleList.count()}")
        println("Number of coins: ${coinsList.count()}")
    }

    fun spawnObstacle(){
        obstacle = Obstacle(context)
        obstacle.posY = objectRow!!.toFloat()
        var lane: Int = generateRandomNumber()
        if(lane == 1)
            obstacle.posX = leftLaneX!!.toFloat()
        else if(lane == 2){
            obstacle.posX = middleLaneX!!.toFloat()
        }
        else if(lane == 3){
            obstacle.posX = rightLaneX!!.toFloat()
        }
        obstacle.speed = gameSpeed
        obstacleList.add(obstacle)
    }

    fun spawnCoin(){
        coins = Coins(context)
        coins.posY = objectRow!!.toFloat()
        var lane: Int = generateRandomNumber()
        if(lane == 1)
            coins.posX = leftLaneX!!.toFloat()
        else if(lane == 2){
            coins.posX = middleLaneX!!.toFloat()
        }
        else if(lane == 3){
            coins.posX = rightLaneX!!.toFloat()
        }
        coins.speed = gameSpeed
        coinsList.add(coins)
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
                    println("SWIPE DISTANCE: $distance")
                    // Determine swipe direction
                    val swipeDirection = when {
                        distance > 100 -> "Right"
                        distance < -100 -> "Left"
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
    }

    private fun updateSpeed(){
        if(gameSpeed!! <=50){
            gameSpeed = gameSpeed!! + 1
            obstacleList.forEach {
                it.speed = gameSpeed
            }
            coinsList.forEach {
                it.speed = gameSpeed
            }
        }

        if(timeInterval>200L){
            timeInterval -= 100L
        }
        println("GAMESPEED: $gameSpeed. TIMEINTERVAL: $timeInterval")
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
        objectRow = -100 //This should be something like -100 to spawn above the screen and then fall down
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

