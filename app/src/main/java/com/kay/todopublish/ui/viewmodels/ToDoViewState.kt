package com.kay.todopublish.ui.viewmodels

import com.kay.todopublish.util.SearchAppBarState

// todo (1)
data class ToDoViewState(
    // Top bar
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    val searchTextInputState: String = ""
)
