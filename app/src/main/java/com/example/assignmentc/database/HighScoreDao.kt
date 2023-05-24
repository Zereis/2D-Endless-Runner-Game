package com.example.assignmentc.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT :limit")
    fun getHighScores(limit: Int): List<HighScore>

    @Insert
    fun insertHighScore(highScore: HighScore)

    @Delete
    fun deleteHighScore(highScore: HighScore)
}