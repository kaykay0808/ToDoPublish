package com.kay.todopublish.ui.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.RequestState
import com.kay.todopublish.util.SearchAppBarState

@Composable
fun ListContent(
    allTask: RequestState<List<TaskData>>,
    searchedTask: RequestState<List<TaskData>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    // Logic that decide if we are going to display "searched task" or "all task"
    /**---------- Searched Task Shown ----------*/
    if (searchAppBarState == SearchAppBarState.TRIGGERED) { /* <- Set searchAppBarState to triggered in viewModel */
        if (searchedTask is RequestState.Success) {
            HandleListContentState(
                taskFromList = searchedTask.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    } else {
        if (allTask is RequestState.Success) {
            /** Empty task shown */
            if (allTask.data.isEmpty()) {
                EmptyContent()
            } else {
                /** All task shown */
                DisplayTask(
                    taskFromList = allTask.data,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        }
    }
}

@Composable
fun HandleListContentState(
    taskFromList: List<TaskData>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (taskFromList.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            taskFromList = taskFromList,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

// Function that will display task when content is not empty
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayTask(
    taskFromList: List<TaskData>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = taskFromList,
            key = { task ->
                task.id
            }
        ) { task ->
            TaskItem(
                modifier = Modifier.animateItemPlacement(),
                taskData = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}
