package com.kay.todopublish.ui.screens.list.topbar

import androidx.compose.runtime.Composable
import com.kay.todopublish.ui.viewmodels.ToDoViewModel
import com.kay.todopublish.util.SearchAppBarState

@Composable
fun ListTopBar(
    // Todo
    toDoViewModel: ToDoViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String // ->
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchIconClicked = {
                    toDoViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortIconClicked = {},
                onDeleteIconClicked = {}
            )
        }
        else -> {
            SearchAppBar(
                textSearchInput = searchTextState,
                onTextChange = { onNewTextEdit ->
                    toDoViewModel.searchTextState.value = onNewTextEdit
                },
                onCloseClicked = {
                    toDoViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    toDoViewModel.searchTextState.value = ""
                },
                onSearchClicked = {}
            )
        }
    }
}
