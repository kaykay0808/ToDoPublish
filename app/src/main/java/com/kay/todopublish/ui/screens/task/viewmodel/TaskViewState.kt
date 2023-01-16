package com.kay.todopublish.ui.screens.task.viewmodel

import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData

data class TaskViewState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,

    val selectedTask: TaskData? = null // RequestState<TaskData> = RequestState.Idle
)
