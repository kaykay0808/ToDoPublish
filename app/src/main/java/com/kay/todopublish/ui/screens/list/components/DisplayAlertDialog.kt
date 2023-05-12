package com.kay.todopublish.ui.screens.list.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.kay.todopublish.R

@Composable
fun DisplayAlertDialog(
    title: String, // Title?
    dialogMessage: String,
    openAlertDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    // Whenever the dialog is true (open)
    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            // The text which presents the details regarding the Dialog's purpose
            text = {
                Text(
                    text = dialogMessage,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            // Positive button
            confirmButton = {
                Button(
                    onClick = {
                        // onYesClicked will trigger {navigateToListScreen(Action.DELETE)} from ExistingTaskTopBar (TaskScreen)
                        // and {onDeleteAllConfirmed()}
                        onYesClicked()
                        closeDialog()
                    }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            // Negative Button
            dismissButton = {
                OutlinedButton(
                    onClick = { closeDialog() }
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            onDismissRequest = { closeDialog() }
        )
    }
}
