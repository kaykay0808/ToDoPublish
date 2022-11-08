package com.kay.todopublish.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun ListContent() {
    // Todo
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    taskData: TaskData,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(taskData.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = taskData.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(PRIORITY_INDICATOR_SIZE)
                            .height(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = taskData.priority.color
                        )
                    }
                }
            }
            // Description Text under the title.
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = taskData.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis /* This value will give dots in the end of text if line is more then 2*/
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        taskData = TaskData(
            0,
            "This is a Random Title",
            "Very Hard Task",
            Priority.HIGH
        ),
        navigateToTaskScreen = {}
    )
}
