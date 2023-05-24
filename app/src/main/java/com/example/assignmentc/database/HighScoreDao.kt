package com.example.assignmentc.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Insert
    suspend fun insert(highScore: HighScore)

    @Query("SELECT * FROM high_scores ORDER BY score DESC")
    suspend fun getHighScores(): List<HighScore>
}