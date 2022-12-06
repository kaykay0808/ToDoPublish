package com.kay.todopublish.ui.screens.task.topbar

import androidx.compose.runtime.Composable
import com.kay.todopublish.util.Action

@Composable
fun TaskTopBar(
    navigateToListScreen: (Action) -> Unit,
) {
    NewTaskTopBar(navigateToListScreen = navigateToListScreen)
}
