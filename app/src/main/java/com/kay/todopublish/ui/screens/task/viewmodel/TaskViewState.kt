package com.kay.todopublish.ui.screens.task.viewmodel

import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.RequestState

data class TaskViewState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,

    val selectedTask: RequestState<TaskData> = RequestState.Idle
)
