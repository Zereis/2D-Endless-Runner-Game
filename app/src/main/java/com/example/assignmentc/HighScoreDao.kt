package com.example.assignmentc

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HighScoreDao {
    @Insert
    suspend fun insert(highScore: HighScore)

    @Update
    suspend fun update(highScore: HighScore)

    @Delete
    suspend fun delete(highScore: HighScore)

    @Query("SELECT * FROM high_score_table ORDER BY score DESC")
    suspend fun getAllHighScores(): List<HighScore>

    @Query("SELECT * FROM high_score_table ORDER BY score DESC LIMIT 1")
    suspend fun getHighestScore(): HighScore?
}