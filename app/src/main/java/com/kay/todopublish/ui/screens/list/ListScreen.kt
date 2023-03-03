package com.kay.todopublish.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
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
import com.kay.todopublish.util.CloseIconState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit
    // action: Action
) {
    val listViewModel: ListViewModel = hiltViewModel()
    val viewState = listViewModel.viewState
    val allTask = viewState.allTask
    val scaffoldState = rememberScaffoldState()

    // Log.d("LIST_ACTION", "$action")

    ViewEffects(listViewModel.viewEffects) {
        when (it) {
            is ListViewEffect.ShowSnackBar -> if (it.action != Action.NO_ACTION) {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = it.message,
                    actionLabel = "Ok"
                )
            }
        }
        Log.d("SNACK_BAR_ACTION_INSIDE", "$it")
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListTopBar(
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
                },
                viewState = viewState,
                onSearchImeClicked = { searchQuery ->
                    listViewModel.searchDatabase(searchQuery = searchQuery)
                },
                onSearchTextChange = { onNewTextEdit ->
                    listViewModel.newInputTextChange(onNewTextEdit)
                },
                onDeleteAllIconClicked = {
                    listViewModel.deleteAllTask()
                    listViewModel.setActions()
                }
            )
        },
        content = {
            ListContent(
                allTask = allTask,
                searchedTask = viewState.searchTask,
                searchAppBarState = viewState.searchAppBarState,
                navigateToTaskScreen = navigateToTaskScreen
            )
            /*DisplaySnackBar(
                scaffoldState = scaffoldState,
                action = viewState.actionForSnackBar,
                message = listViewModel.setMessage(action = viewState.actionForSnackBar),
                // handleDatabaseAction = {listViewModel.databaseActionManageList(action = action)},
                // databaseActionManageList = { listViewModel.databaseActionManageList(action = viewState.actionForSnackBar) },
            )*/
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

/*@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    action: Action,
    message: String
    // databaseActionManageList: () -> Unit,
    // taskTitle: String,
    // onUndoClicked: (Action) -> Unit,
    // listViewModel: ListViewModel
) {
    Log.d("SNACK_BAR_ACTION", "$action")
    // databaseActionManageList()
    // val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        Log.d("SNACK_BAR_ACTION_INSIDE", "$action")
        if (action != Action.NO_ACTION) {
            val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Ok"
            )
        }
        // scope.launch {}
    }
}*/
