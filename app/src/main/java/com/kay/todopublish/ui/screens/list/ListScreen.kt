package com.kay.todopublish.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.R
import com.kay.todopublish.ui.screens.list.topbar.ListTopBar
import com.kay.todopublish.ui.theme.floatingActionButtonBackgroundColor
import com.kay.todopublish.ui.viewmodels.ToDoViewState
import com.kay.todopublish.util.SearchAppBarState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    viewState: ToDoViewState,
    onSearchIconClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTextChange: (String) -> Unit
    // toDoViewModel: ToDoViewModel,
) {

    // val searchAppBarState: SearchAppBarState by toDoViewModel.searchAppBarState
    // val searchTextState: String by toDoViewModel.searchTextState
    Scaffold(
        topBar = {
            ListTopBar(
                onSearchIconClicked = onSearchIconClicked,
                onCloseClicked = onCloseClicked,
                viewState = viewState,
                onSearchTextChange = onSearchTextChange,
                // toDoViewModel = toDoViewModel,
                // searchAppBarState = searchAppBarState,
                // searchTextState = searchTextState,
            )
        },
        content = {},
        floatingActionButton = {
            ListFloatingActionButton(onFloatingActionButtonClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFloatingActionButton(onFloatingActionButtonClicked: (taskId: Int) -> Unit) {
    // Define our floating action button
    FloatingActionButton(
        onClick = {
            onFloatingActionButtonClicked(-1/*(<- Assign an id number here)*/)
        },
        backgroundColor = MaterialTheme.colors.floatingActionButtonBackgroundColor
    ) {
        // Add an icon to our floating action button
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(
        navigateToTaskScreen = {},
        viewState = ToDoViewState(SearchAppBarState.CLOSED),
        onSearchIconClicked = {},
        onSearchTextChange = {},
        onCloseClicked = {},
    )
}
