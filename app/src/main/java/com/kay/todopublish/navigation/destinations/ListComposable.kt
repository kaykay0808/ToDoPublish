package com.kay.todopublish.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kay.todopublish.ui.screens.list.ListScreen
import com.kay.todopublish.ui.viewmodels.ToDoViewModel
import com.kay.todopublish.util.CloseIconState
import com.kay.todopublish.util.Constants.LIST_ARGUMENT_KEY
import com.kay.todopublish.util.Constants.LIST_SCREEN

// extension function listComposable
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(
            navArgument(LIST_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        val toDoViewModel: ToDoViewModel = hiltViewModel()
        val viewState = toDoViewModel.viewState
        val allTask = viewState.allTask

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            viewState = viewState,
            onSearchIconClicked = { toDoViewModel.openSearchBar() },
            onCloseIconClicked = {
                when (viewState.closeIconState) {
                    CloseIconState.READY_TO_EMPTY_FIELD -> {
                        toDoViewModel.defaultTextInputState() // Tømme
                        toDoViewModel.readyToCloseSearchBar() // klar til å lukke searchBar
                    }
                    CloseIconState.READY_TO_CLOSE_SEARCH_BAR -> {
                        if (viewState.searchTextInputState.isNotEmpty()) {
                            toDoViewModel.defaultTextInputState()
                        } else {
                            toDoViewModel.closeSearchBar()
                            toDoViewModel.readyToEmptyField() // set back to READY_TO_EMPTY_FIELD
                        }
                    }
                }
                // toDoViewModel.closeSearchBar()
                // toDoViewModel.defaultTextInputState()
            },
            onSearchTextChange = { onNewTextEdit ->
                toDoViewModel.newInputTextChange(onNewTextEdit)
            },
            task = allTask
        )
    }
}
