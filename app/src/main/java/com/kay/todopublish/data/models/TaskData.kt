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
/** APP inspection test */
// INSERT INTO todo_table (id, title, description, priority) VALUES(0, "TaskTitle", "SomeDescription", "LOW")
// DELETE FROM todo_table
