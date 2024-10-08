package com.kay.todopublish.ui.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kay.todopublish.ui.ViewEffects
import com.kay.todopublish.ui.screens.task.topbar.TaskTopBar
import com.kay.todopublish.ui.screens.task.viewmodel.TaskViewEffects
import com.kay.todopublish.ui.screens.task.viewmodel.TaskViewModel
import com.kay.todopublish.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    taskId: Int,
    navigateToListScreen: (/*Action*/) -> Unit
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    val taskViewState = taskViewModel.taskViewState
    val selectedTask = taskViewState.selectedTask
    val context = LocalContext.current

    LaunchedEffect(key1 = taskId) {
        taskViewModel.getSelectedTask(taskId = taskId)
    }
    LaunchedEffect(key1 = selectedTask) {
        if (selectedTask != null || taskId == -1) {
            taskViewModel.updateTaskField(selectedTask = selectedTask)
        }
    }

    ViewEffects(taskViewModel.viewEffects) {
        when (it) {
            TaskViewEffects.NavigateBack -> navigateToListScreen()
            TaskViewEffects.DisplayErrorToast -> displayToast(context = context)
        }
    }

    Log.d("TASK_SCREEN", "$selectedTask")
    Scaffold(
        topBar = {
            TaskTopBar(
                selectedTask = taskViewState.selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen()
                    } else {
                        taskViewModel.manageDatabaseAction(
                            action = action
                        )
                    }
                }
            )
        },
        content = {
            TaskContent(
                title = taskViewState.title,
                description = taskViewState.description,
                priority = taskViewState.priority,
                onTitleChange = { taskViewModel.updateTitle(newTitle = it) },
                onDescriptionChange = { taskViewModel.updateDescription(newDescription = it) },
                onPriorityChange = { taskViewModel.updatePriority(newPriority = it) }
            )
        }
    )
}

// A Toast warning if fields are empty in the task screen
private fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Text fields empty, please fill in the title and the description",
        Toast.LENGTH_SHORT
    ).show()
}
