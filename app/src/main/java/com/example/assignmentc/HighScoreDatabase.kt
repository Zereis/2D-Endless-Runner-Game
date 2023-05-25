package com.example.assignmentc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [HighScore::class], version = 1, exportSchema = false)
abstract class HighScoreDatabase : RoomDatabase() {
    abstract fun highScoreDao(): HighScoreDao

    companion object {
        @Volatile
        private var INSTANCE: HighScoreDatabase? = null

        fun getDatabase(context: Context): HighScoreDatabase {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HighScoreDatabase::class.java,
                    "high_scores"

                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}