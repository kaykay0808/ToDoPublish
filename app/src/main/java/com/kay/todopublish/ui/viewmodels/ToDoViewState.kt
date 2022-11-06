package com.kay.todopublish.ui.viewmodels

import com.kay.todopublish.util.CloseIconState
import com.kay.todopublish.util.SearchAppBarState

data class ToDoViewState(
    // Top bar
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    val searchTextInputState: String = "",
    val closeIconState: CloseIconState = CloseIconState.READY_TO_EMPTY_FIELD
)
