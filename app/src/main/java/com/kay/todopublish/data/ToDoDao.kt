package com.kay.todopublish.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kay.todopublish.data.models.TaskData
import kotlinx.coroutines.flow.Flow

// https://www.w3schools.com/sql/

@Dao
interface ToDoDao {
    // Inserting the data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(taskData: TaskData)

    // Reading all Data
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): Flow<List<TaskData>>

    @Query("SELECT * FROM todo_table WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<TaskData>

    @Update
    suspend fun updateTask(taskData: TaskData)

    @Delete
    suspend fun deleteTask(taskData: TaskData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTask()

    // Search Title and description
    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): Flow<List<TaskData>>

    // Sort the low priorities first
    @Query("SELECT * From todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<TaskData>>

    // Sort the high priorities first
    @Query("SELECT * From todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<TaskData>>
}
