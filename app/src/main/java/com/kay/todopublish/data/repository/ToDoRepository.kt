package com.kay.todopublish.data.repository

import com.kay.todopublish.data.ToDoDao
import com.kay.todopublish.data.models.TaskData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    // access all functions from Dao
    val getAllTasks: Flow<List<TaskData>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<TaskData>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<TaskData>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<TaskData> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(taskData: TaskData) {
        toDoDao.addTask(taskData = taskData)
    }

    suspend fun updateTask(taskData: TaskData) {
        toDoDao.updateTask(taskData = taskData)
    }

    suspend fun deleteTask(taskData: TaskData) {
        toDoDao.deleteTask(taskData = taskData)
    }

    suspend fun deleteAllTask() {
        toDoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<TaskData>> {
        return toDoDao.searchDataBase(searchQuery = searchQuery)
    }
}
