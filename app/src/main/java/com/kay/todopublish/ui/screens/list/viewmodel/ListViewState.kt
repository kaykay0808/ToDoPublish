package com.kay.todopublish.ui.screens.list.viewmodel

import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.CloseIconState
import com.kay.todopublish.util.RequestState
import com.kay.todopublish.util.SearchAppBarState

data class ListViewState(
    // Top bar
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    val searchTextInputState: String = "",
    val closeIconState: CloseIconState = CloseIconState.READY_TO_EMPTY_FIELD,
    // Room
    val allTask: RequestState<List<TaskData>> = RequestState.Idle, // List<TaskData> = emptyList()
    val actionForSnackBar: Action = Action.NO_ACTION,
    val searchTask: RequestState<List<TaskData>> = RequestState.Idle,
    // Single Task
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    // val recentlyDeletedSingleTask: TaskData? = null

    )
