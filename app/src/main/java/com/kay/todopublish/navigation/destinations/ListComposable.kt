package com.kay.todopublish.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kay.todopublish.ui.screens.list.ListScreen
import com.kay.todopublish.ui.screens.list.viewmodel.ListViewModel
import com.kay.todopublish.util.CloseIconState
import com.kay.todopublish.util.Constants.LIST_ARGUMENT_KEY
import com.kay.todopublish.util.Constants.LIST_SCREEN
import com.kay.todopublish.util.toAction

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
    ) { navBackStackEntry ->

        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        val listViewModel: ListViewModel = hiltViewModel()
        val viewState = listViewModel.viewState
        val allTask = viewState.allTask

        LaunchedEffect(key1 = action) {
            viewState.action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            viewState = viewState,
            onSearchIconClicked = { listViewModel.openSearchBar() },
            onCloseIconClicked = {
                when (viewState.closeIconState) {
                    CloseIconState.READY_TO_EMPTY_FIELD -> {
                        listViewModel.defaultTextInputState() // Empty field
                        listViewModel.readyToCloseSearchBar() // Ready to close SearchBar.
                    }
                    CloseIconState.READY_TO_CLOSE_SEARCH_BAR -> {
                        if (viewState.searchTextInputState.isNotEmpty()) {
                            listViewModel.defaultTextInputState()
                        } else {
                            listViewModel.closeSearchBar()
                            listViewModel.readyToEmptyField() // set back to READY_TO_EMPTY_FIELD
                        }
                    }
                }
                // toDoViewModel.closeSearchBar()
                // toDoViewModel.defaultTextInputState()
            },
            onSearchTextChange = { onNewTextEdit ->
                listViewModel.newInputTextChange(onNewTextEdit)
            },
            task = allTask
        )
    }
}
