package com.kay.todopublish.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kay.todopublish.data.models.TaskData

// entities is what table we want to connect.
@Database(entities = [TaskData::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}
