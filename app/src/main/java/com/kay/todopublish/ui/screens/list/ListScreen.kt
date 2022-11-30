package com.kay.todopublish.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.kay.todopublish.R
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.ui.screens.list.topbar.ListTopBar
import com.kay.todopublish.ui.theme.floatingActionButtonBackgroundColor
import com.kay.todopublish.ui.viewmodels.ToDoViewState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    viewState: ToDoViewState,
    onSearchIconClicked: () -> Unit,
    onCloseIconClicked: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    // toDoViewModel: ToDoViewModel,
    task: List<TaskData>
) {
    // LaunchedEffect(key1 = true) { toDoViewModel.getAllTask() }
    // val allTask by sharedViewModel.allTask.collectAsState()
    Scaffold(
        topBar = {
            ListTopBar(
                onSearchIconClicked = onSearchIconClicked,
                onCloseIconClicked = onCloseIconClicked,
                viewState = viewState,
                onSearchTextChange = onSearchTextChange,
            )
        },
        content = {
            ListContent(
                task = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
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
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

/*@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(
        navigateToTaskScreen = {},
        viewState = ToDoViewState(SearchAppBarState.CLOSED),
        onSearchIconClicked = {},
        onSearchTextChange = {},
        onCloseIconClicked = {},
    )
}*/
