package com.kay.todopublish.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.ui.screens.task.topbar.TaskTopBar
import com.kay.todopublish.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    selectedTask: TaskData?,
    navigateToListScreen: (Action) -> Unit
) {
    Scaffold(
        topBar = {
            TaskTopBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {}
    )
}

@Composable
@Preview
fun TaskScreenPreview() {
    // TaskScreen(navigateToListScreen = {})
}
