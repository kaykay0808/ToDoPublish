package com.kay.todopublish.ui.screens.task.topbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.R
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.ui.screens.list.components.DisplayAlertDialog
import com.kay.todopublish.ui.theme.topAppBarBackgroundColor
import com.kay.todopublish.ui.theme.topAppBarContentColor
import com.kay.todopublish.util.Action

@Composable
fun ExistingTaskTopBar(
    selectedTask: TaskData,
    navigateToListScreens: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            // designing a back action icon function
            CloseAction(onCloseClicked = navigateToListScreens)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis /* Adds 3 dots */
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ExistingTaskBarActions(
                selectedTask = selectedTask,
                navigateToListScreens = navigateToListScreens
            )
        }
    )
}

@Composable
fun ExistingTaskBarActions(
    selectedTask: TaskData,
    navigateToListScreens: (Action) -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    DisplayAlertDialog(
        title = stringResource(
            id = R.string.delete_task,
            selectedTask.title
        ),
        dialogMessage = stringResource(
            id = R.string.delete_task_confirmation,
            selectedTask.title
        ),
        openAlertDialog = openAlertDialog,
        closeDialog = { openAlertDialog = false },
        onYesClicked = { navigateToListScreens(Action.DELETE) }
    )
    DeleteAction(
        onDeleteClicked = { openAlertDialog = true }
    )
    UpdateAction(onUpdateClicked = navigateToListScreens)
}

/** --------------------------- Button Actions ------------------------------------ */
@Composable
fun CloseAction(onCloseClicked: (Action) -> Unit) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(onDeleteClicked: () -> Unit) {
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(onUpdateClicked: (Action) -> Unit) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Preview
@Composable
fun ExistingTaskTopBarPreview() {
    ExistingTaskTopBar(
        selectedTask = TaskData(
            0,
            "Task Title",
            "Some Description",
            Priority.HIGH
        ),
        navigateToListScreens = {}
    )
}
