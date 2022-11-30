package com.kay.todopublish.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.ui.theme.LARGE_PADDING
import com.kay.todopublish.ui.theme.PRIORITY_INDICATOR_SIZE
import com.kay.todopublish.ui.theme.TASK_ITEM_ELEVATION
import com.kay.todopublish.ui.theme.taskItemBackgroundColor
import com.kay.todopublish.ui.theme.taskItemTextColor

@Composable
fun ListContent(
    task: List<TaskData>, // task: List<TaskData>
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (task.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            task = task,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

// Logic that will display task when content is not empty
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


