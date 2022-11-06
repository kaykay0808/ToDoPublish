package com.kay.todopublish.ui.screens.list.topbar

import androidx.compose.runtime.Composable
import com.kay.todopublish.ui.viewmodels.ToDoViewState
import com.kay.todopublish.util.SearchAppBarState

@Composable
fun ListTopBar(
    viewState: ToDoViewState,
    onSearchIconClicked: () -> Unit,
    onCloseIconClicked: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    // toDoViewModel: ToDoViewModel,
    // searchAppBarState: SearchAppBarState,
    // searchTextState: String // ->
) {
    when (viewState.searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchIconClicked = onSearchIconClicked, // { toDoViewModel.openSearchBar() },
                onSortIconClicked = {},
                onDeleteIconClicked = {}
            )
        }
        else -> {
            SearchAppBar(
                textSearchInput = viewState.searchTextInputState,
                onSearchTextChange = onSearchTextChange,
                onCloseIconClicked = onCloseIconClicked,
                onSearchClicked = {}
            )
        }
    }
}
