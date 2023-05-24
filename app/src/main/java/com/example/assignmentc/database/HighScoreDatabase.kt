package com.example.assignmentc.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HighScore::class], version = 1)
abstract class HighScoreDatabase : RoomDatabase() {
    abstract fun highScoreDao(): HighScoreDao
}