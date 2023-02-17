package com.kay.todopublish.ui.screens.task.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.ui.ViewEffects
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.Constants.MAX_TITLE_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    /** == VIEW-STATES == */
    var taskViewState by mutableStateOf(TaskViewState())
        private set

    private var id = 0
    private var title = ""
    private var description = ""
    private var priority = Priority.LOW
    private var actionTaskScreen = Action.NO_ACTION
    private var selectedTask: TaskData? = null

    private fun render() {
        taskViewState = TaskViewState(
            id = id,
            title = title,
            description = description,
            priority = priority,
            selectedTask = selectedTask,
            actionTaskScreen = actionTaskScreen
        )
    }

    /** == VIEW-EFFECTS ==*/

    val viewEffects = ViewEffects<TaskViewEffects>()

    // Create, Read, Update, Delete
    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                selectedTask = task
                render()
            }
        }
    }

    fun handleAction(action: Action) {
        if (validateFields()) {
            when (action) {
                Action.ADD -> {
                    addTask()
                }

                Action.UPDATE -> {
                    updateTask()
                }

                Action.DELETE -> {
                    deleteSingleTask()
                }

                else -> {
                    // no database operation, just navigate back
                }
            }
            viewEffects.send(TaskViewEffects.NavigateBack)
        } else {
            viewEffects.send(TaskViewEffects.DisplayErrorToast)
        }
    }

    // Create, Read, Update, Delete
    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskData = TaskData(
                title = title,
                description = description,
                priority = priority
            )
            repository.addTask(taskData = taskData)
            render()
        }
    }

    // Create, Read, Update, Delete
    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val updateTaskData = TaskData(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.updateTask(taskData = updateTaskData)
            render()
        }
    }

    // Create, Read, Update, Delete
    private fun deleteSingleTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val deleteSingleTaskData = TaskData(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.deleteTask(taskData = deleteSingleTaskData)
            render()
        }
    }

    // Function that updating our mutableState values (Title, description, priority)
    fun updateTaskField(selectedTask: TaskData?) {
        // Check if selectedTask is null (if we have clicked on the specific task)
        if (selectedTask != null) {
            Log.d("updateTaskField", selectedTask.toString())
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

    // A function updating the title and limit the title length to 20 characters.
    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
        render()
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
        render()
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
        render()
    }

    // Validation if our field in Task Screen is empty
    private fun validateFields(): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }
}

sealed interface TaskViewEffects {
    object NavigateBack : TaskViewEffects
    object DisplayErrorToast: TaskViewEffects
}
