package com.kay.todopublish.ui.screens.task

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.kay.todopublish.ui.screens.task.topbar.TaskTopBar
import com.kay.todopublish.ui.screens.task.viewmodel.TaskViewModel
import com.kay.todopublish.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    taskId: Int,
    navigateToListScreen: (Action) -> Unit,
) {
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
    Log.d("TASK_SCREEN", "$selectedTask")
    Scaffold(
        topBar = {
            TaskTopBar(
                selectedTask = taskViewState.selectedTask,
                navigateToListScreen = navigateToListScreen,
            )
        },
        content = {
            TaskContent(
                title = taskViewState.title,
                description = taskViewState.description,
                priority = taskViewState.priority,
                onTitleChange = { taskViewModel.updateTitle(it) },
                onDescriptionChange = { taskViewState.description },
                onPriorityChange = { taskViewState.priority }
            )
        }
    )
}
