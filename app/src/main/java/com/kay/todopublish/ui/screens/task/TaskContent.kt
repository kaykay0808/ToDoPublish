package com.kay.todopublish.ui.screens.task

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kay.todopublish.R
import com.kay.todopublish.components.PriorityDropDownMenu
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.ui.theme.LARGE_PADDING
import com.kay.todopublish.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    description: String,
    priority: Priority,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit
) {
    Log.d("TASK_CONTENT","$onTitleChange ,$onDescriptionChange")
    Column(
        modifier = Modifier
            .fillMaxSize() // Takes the whole content in the Scaffold
            .background(MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true // Allows the user to only write in 1 line
        )
        Divider(
            modifier = Modifier
                .height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        PriorityDropDownMenu(
            priority = priority,
            onPrioritySelected = onPriorityChange
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
fun TaskContentPreview() {
    TaskContent(
        title = "This is a Title",
        description = "Some random description",
        priority = Priority.HIGH,
        onTitleChange = {},
        onDescriptionChange = {},
        onPriorityChange = {}
    )
}
