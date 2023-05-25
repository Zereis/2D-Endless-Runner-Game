package com.example.assignmentc

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_score_table")
data class HighScore(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val score: Int
)