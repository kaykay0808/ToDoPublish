package com.kay.todopublish.ui.screens.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.RequestState

@Composable
fun ListContent(
    task: RequestState<List<TaskData>>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (task is RequestState.Success) {
        if (task.data.isEmpty()) {
            EmptyContent()
        } else {
            DisplayTask(
                task = task.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

// Function that will display task when content is not empty
@Composable
fun DisplayTask(
    task: List<TaskData>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = task,
            key = { task ->
                task.id
            }
        ) { task ->
            TaskItem(
                taskData = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}
