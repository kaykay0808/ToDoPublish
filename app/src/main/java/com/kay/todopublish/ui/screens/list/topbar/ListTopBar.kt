package com.kay.todopublish.ui.screens.list.topbar

import androidx.compose.runtime.Composable
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.ui.screens.list.viewmodel.ListViewState
import com.kay.todopublish.util.SearchAppBarState

@Composable
fun ListTopBar(
    viewState: ListViewState,
    onSearchIconClicked: () -> Unit,
    onSortIconClicked: (Priority) -> Unit,
    onCloseIconClicked: () -> Unit,
    onSearchImeClicked: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onDeleteAllConfirmed: () -> Unit
    // toDoViewModel: ToDoViewModel,
    // searchAppBarState: SearchAppBarState,
    // searchTextState: String // ->
) {
    when (viewState.searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchIconClicked = onSearchIconClicked, // { toDoViewModel.openSearchBar() },
                onSortIconClicked = onSortIconClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        }
        else -> {
            SearchAppBar(
                textSearchInput = viewState.searchTextInputState,
                onSearchTextChange = onSearchTextChange,
                onCloseIconClicked = onCloseIconClicked,
                onSearchImeClicked = onSearchImeClicked
            )
        }
    }
}
