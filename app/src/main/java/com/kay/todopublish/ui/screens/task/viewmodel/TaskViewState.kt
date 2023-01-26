package com.kay.todopublish.ui.screens.task.viewmodel

import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.Action

data class TaskViewState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,

    val actionTaskScreen: Action = Action.NO_ACTION,

    val selectedTask: TaskData? = null // RequestState<TaskData> = RequestState.Idle
)
