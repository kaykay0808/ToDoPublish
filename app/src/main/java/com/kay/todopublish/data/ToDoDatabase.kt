package com.kay.todopublish.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kay.todopublish.data.models.TaskData

@Database(entities = [TaskData::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}
