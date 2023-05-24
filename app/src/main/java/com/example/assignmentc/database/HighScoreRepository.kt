package com.example.assignmentc.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HighScoreRepository(context: Context) {
    private val highScoreDatabase: HighScoreDatabase = Room.databaseBuilder(
        context.applicationContext,
        HighScoreDatabase::class.java,
        "high_scores.db"
    ).build()

    private val highScoreDao: HighScoreDao = highScoreDatabase.highScoreDao()

    suspend fun insertHighScore(highScore: HighScore) = withContext(Dispatchers.IO) {
        highScoreDao.insert(highScore)
    }

    suspend fun getHighScores(): List<HighScore> = withContext(Dispatchers.IO) {
        highScoreDao.getHighScores()
    }
}