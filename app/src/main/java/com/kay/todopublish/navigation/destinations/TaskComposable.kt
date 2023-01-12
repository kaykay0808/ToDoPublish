package com.kay.todopublish.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kay.todopublish.ui.screens.list.viewmodel.ListViewModel
import com.kay.todopublish.ui.screens.task.TaskScreen
import com.kay.todopublish.ui.screens.task.topbar.viewmodel.TaskViewModel
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
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)

        // val listViewModel: ListViewModel = hiltViewModel()
        val taskViewModel: TaskViewModel = hiltViewModel()
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

        if (selectedTask != null) {
            TaskScreen(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen,
                title = selectedTask.title,
                description = selectedTask.description,
                priority = selectedTask.priority,
                onTitleChange = { taskViewState.selectedTask.title },
                onDescriptionChange = { taskViewState.selectedTask.description },
                onPriorityChange = { taskViewState.selectedTask.priority }
            )
        }
    }
}
