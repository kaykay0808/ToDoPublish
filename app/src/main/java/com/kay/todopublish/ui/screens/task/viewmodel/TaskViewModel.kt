package com.kay.todopublish.ui.screens.task.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.util.Constants.MAX_TITLE_LENGTH
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
    private var priority = Priority.NONE

    /*init {
        getSelectedTask(taskId = id)
    }*/

    private var selectedTask: TaskData? = null

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
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                selectedTask = task
                render()
            }
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
            priority = Priority.NONE
            render()
        }
    }

    // A function updating the title and limit the title length to 20 characters.
    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
        render()
    }
}
