package com.kay.todopublish.ui.screens.task.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    var taskViewState by mutableStateOf(TaskViewState())
        private set

    private var id = 0
    private var title = ""
    private var description = ""
    private var priority = Priority.LOW

    private var selectedTask: RequestState<TaskData> = RequestState.Idle

    init {
        getSelectedTask(taskId = id)
    }

    private fun render() {
        taskViewState = TaskViewState(
            id = id,
            title = title,
            description = description,
            priority = priority,
            selectedTask = selectedTask
        )
    }

     fun getSelectedTask(taskId: Int) {
        selectedTask = RequestState.Loading
        render()
        try {
            viewModelScope.launch {
                repository.getSelectedTask(taskId = taskId).collect {
                    selectedTask = RequestState.Success(it)
                    render()
                }
            }
        } catch (e: Exception) {
            selectedTask = RequestState.Error(e)
            render()
        }
    }

    // Function that updating our mutableState values (Title, description, priority)
     fun updateTaskField(selectedTask: TaskData?) {
        // Check if selectedTask is null (if we have clicked on the specific task)
        if (selectedTask != null) {
            // set the values of each variable from our mutableState
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
            render()
        } else {
            // Default values
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
            render()
        }
    }

    fun defaultValues() {
        id = 0
        title = ""
        description = ""
        priority = Priority.LOW
        render()
    }
}