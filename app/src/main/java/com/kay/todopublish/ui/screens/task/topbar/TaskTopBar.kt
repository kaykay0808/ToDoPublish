package com.kay.todopublish.ui.screens.task.topbar

import androidx.compose.runtime.Composable
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.Action

@Composable
fun TaskTopBar(
    selectedTask: TaskData?,
    navigateToListScreen: (Action) -> Unit,
) {
    if (selectedTask == null) {
        NewTaskTopBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistingTaskTopBar(
            selectedTask = selectedTask,
            navigateToListScreens = navigateToListScreen
        )
    }
}
