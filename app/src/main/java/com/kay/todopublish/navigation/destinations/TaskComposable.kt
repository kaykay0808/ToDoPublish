package com.kay.todopublish.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kay.todopublish.ui.screens.task.TaskScreen
import com.kay.todopublish.ui.screens.task.viewmodel.TaskViewModel
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.Constants.TASK_ARGUMENT_KEY
import com.kay.todopublish.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        // specify what argument the screen will have
        route = TASK_SCREEN,
        arguments = listOf(
            navArgument(TASK_ARGUMENT_KEY) {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val taskViewModel: TaskViewModel = hiltViewModel()
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        val taskViewState = taskViewModel.taskViewState
        val selectedTask = taskViewState.selectedTask
        LaunchedEffect(key1 = taskId) {
            taskViewModel.getSelectedTask(taskId = taskId)
        }
        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                taskViewModel.updateTaskField(selectedTask = selectedTask)
            }
        }
        TaskScreen(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen,
            taskViewState = taskViewState
            // title = taskViewState.title,
            // description = taskViewState.description,
            // priority = taskViewState.priority,
            // onTitleChange = { taskViewState.title },
            // onDescriptionChange = { taskViewState.description },
            // onPriorityChange = { taskViewState.priority }
        )
    }
}
