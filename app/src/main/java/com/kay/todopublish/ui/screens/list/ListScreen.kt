package com.kay.todopublish.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kay.todopublish.R
import com.kay.todopublish.ui.ViewEffects
import com.kay.todopublish.ui.screens.list.topbar.ListTopBar
import com.kay.todopublish.ui.screens.list.viewmodel.ListViewEffect
import com.kay.todopublish.ui.screens.list.viewmodel.ListViewModel
import com.kay.todopublish.ui.theme.floatingActionButtonBackgroundColor
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.SearchAppBarState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    val listViewModel: ListViewModel = hiltViewModel()
    val viewState = listViewModel.viewState
    val allTask = viewState.allTask
    val scaffoldState = rememberScaffoldState()

    ViewEffects(listViewModel.viewEffects) {
        when (it) {
            is ListViewEffect.ShowSnackBar -> if (it.action != Action.NO_ACTION) {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = it.message,
                    actionLabel = listViewModel.returningActionToString(it.action) // "Ok"
                )
                listViewModel.undoDeletedTask(
                    action = it.action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = it.onUndoClicked
                )
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListTopBar(
                onSearchIconClicked = {
                    // listViewModel.openSearchBar()
                    listViewModel.listAppBarState(
                        newState = SearchAppBarState.OPENED
                    )
                },
                onCloseIconClicked = {
                    if (viewState.searchTextInputState.isNotEmpty()) {
                        listViewModel.defaultTextInputState()
                    } else {
                        // listViewModel.closeSearchBar()
                        listViewModel.listAppBarState(
                            newState = SearchAppBarState.CLOSED
                        )
                    }
                    /*when (viewState.closeIconState) {
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
                    }*/
                },
                viewState = viewState,
                onSearchImeClicked = { searchQuery ->
                    listViewModel.searchDatabase(searchQuery = searchQuery)
                },
                onSearchTextChange = { onNewTextEdit ->
                    listViewModel.newInputTextChange(onNewTextEdit)
                },
                onSortIconClicked = { listViewModel.persistSortState(it) }, // Store the order with priorities. (sending priority to the viewModel)
                onDeleteAllConfirmed = {
                    listViewModel.deleteAllTask()
                    listViewModel.setActions()
                },
                textSearchInput = viewState.searchTextInputState
            )
        },
        content = {
            ListContent(
                allTask = allTask,
                searchedTask = viewState.allTask,
                searchAppBarState = viewState.searchAppBarState,
                onSwipeToDelete = { action, taskData ->
                    listViewModel.updateAction(newAction = action) // todo do we need this line?
                    listViewModel.deleteSingleTaskFromList(taskData = taskData)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() // Dismiss the first snackBar if we swipe multiply items.
                },
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFloatingActionButton(onFloatingActionButtonClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFloatingActionButton(onFloatingActionButtonClicked: (taskId: Int) -> Unit) {
    // Define our floating action button
    FloatingActionButton(
        onClick = {
            onFloatingActionButtonClicked(-1/*(<- Assign an id number here)*/)
        },
        backgroundColor = MaterialTheme.colors.floatingActionButtonBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}
