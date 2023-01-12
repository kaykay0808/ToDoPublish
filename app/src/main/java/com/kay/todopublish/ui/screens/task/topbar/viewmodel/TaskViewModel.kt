package com.kay.todopublish.ui.screens.task.topbar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.util.Constants.TASK_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ToDoRepository
) : ViewModel() {

    var taskViewState by mutableStateOf(TaskViewState())
        private set

    private var selectedTask: TaskData? = null
    private val taskId: Int = checkNotNull(savedStateHandle[TASK_ARGUMENT_KEY])

    init {
        getSelectedTask(taskId = taskId)
    }

    private fun render() {
        taskViewState = TaskViewState(
            selectedTask = selectedTask
        )
    }

    // private val _selectedTask: MutableStateFlow<TaskData?> = MutableStateFlow(null)
    // val selectedTask: StateFlow<TaskData?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                selectedTask = task
                render()
            }
        }
    }
}
