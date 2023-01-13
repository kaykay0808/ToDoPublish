package com.kay.todopublish.ui.screens.task


import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.ui.screens.task.topbar.TaskTopBar
import com.kay.todopublish.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    selectedTask: TaskData?,
    navigateToListScreen: (Action) -> Unit,
    title: String,
    description: String,
    priority: Priority,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit
) {
    // Observing values from our viewModel. (Title, Description, Priority)
    // val titleObserved: String by sharedViewModel.title
    // val descriptionObserved: String by sharedViewModel.description
    // val priorityObserved: Priority by sharedViewModel.priority
    Scaffold(
        topBar = {
            TaskTopBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {
            TaskContent(
                title = title,
                description = description,
                priority = priority,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                onPriorityChange = onPriorityChange
            )
        }
    )
}

@Composable
@Preview
fun TaskScreenPreview() {
    TaskScreen(
        selectedTask = TaskData(
            id = 5,
            title = "THIS IS A PREVIEW",
            description = "Testing description",
            priority = Priority.LOW
        ),
        navigateToListScreen = {},
        title = "All by myself",
        description = "Testing shit",
        priority = Priority.LOW,
        onTitleChange = {},
        onDescriptionChange = {},
        onPriorityChange = {}
    )
}
