package com.kay.todopublish.ui.screens.list.viewmodel

import com.kay.todopublish.data.models.TaskData
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
)
