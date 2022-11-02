package com.kay.todopublish.ui.screens.list.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kay.todopublish.R
import com.kay.todopublish.components.PriorityItem
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.ui.theme.LARGE_PADDING
import com.kay.todopublish.ui.theme.Typography
import com.kay.todopublish.ui.theme.topAppBarContentColor

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    // Calling our 3 action functions which we Define in our 3 different functions
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

// Actions.

// action 1
@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    // Define an Icon for the action
    IconButton(
        onClick = { onSearchClicked() }
    ) { // define the icon appearance
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

// action 2
@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expandedDropDownMenu by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expandedDropDownMenu = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expandedDropDownMenu,
            onDismissRequest = { expandedDropDownMenu = false }
        ) {
            // Items are defined under the component package inside the PriorityItem file.
            // Item 1
            DropdownMenuItem(
                onClick = {
                    expandedDropDownMenu = false
                    onSortClicked(Priority.LOW)
                }
            ) {
                // Defining the row item
                PriorityItem(priority = Priority.LOW)
            }

            // Item 2
            DropdownMenuItem(
                onClick = {
                    expandedDropDownMenu = false
                    onSortClicked(Priority.HIGH)
                }
            ) {
                // Defining the row item
                PriorityItem(priority = Priority.HIGH)
            }

            // Item 3
            DropdownMenuItem(
                onClick = {
                    expandedDropDownMenu = false
                    onSortClicked(Priority.NONE)
                }
            ) {
                // Defining the row item
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

// action 3
@Composable
fun DeleteAllAction(onDeleteClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteClicked()
                }
            ) {
                Text(
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    text = stringResource(id = R.string.delete_all_action),
                    style = Typography.subtitle2
                )
            }
        }
    }
}
