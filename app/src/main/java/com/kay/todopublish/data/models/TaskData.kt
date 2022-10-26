package com.kay.todopublish.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kay.todopublish.util.Constants


@Entity(tableName = Constants.DATABASE_TABLE)
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
