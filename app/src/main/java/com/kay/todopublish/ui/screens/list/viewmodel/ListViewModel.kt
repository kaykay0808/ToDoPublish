package com.kay.todopublish.ui.screens.list.viewmodel

import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.CloseIconState
import com.kay.todopublish.util.RequestState
import com.kay.todopublish.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    /** ============ Search Bar State ================= */
    var viewState by mutableStateOf(ListViewState())
        private set

    private var searchAppBarState = SearchAppBarState.CLOSED
    private var searchTextInputState = ""
    private var closeIconState = CloseIconState.READY_TO_EMPTY_FIELD
    private var allTask: RequestState<List<TaskData>> = RequestState.Idle
    private var searchTask: RequestState<List<TaskData>> = RequestState.Idle
    private var actionListScreen = Action.NO_ACTION

    init {
        getAllTask()
    }

    private fun render() {
        viewState = ListViewState(
            searchAppBarState = searchAppBarState,
            searchTextInputState = searchTextInputState,
            closeIconState = closeIconState,
            allTask = allTask,
            searchTask = searchTask,
            actionListScreen = actionListScreen
        )
    }

    // Get All Task
    // private val _allTask = List<TaskData>(emptyList())
    // val allTask: StateFlow<List<TaskData>> = _allTask
    private fun getAllTask() {
        allTask = RequestState.Loading
        render()
        try {
            viewModelScope.launch {
                repository.getAllTask.collect {
                    allTask = RequestState.Success(it)
                    render()
                }
            }
        } catch (e: Exception) {
            allTask = RequestState.Error(e)
            render()
        }
    }

    fun searchDatabase(searchQuery: String) {
        searchTask = RequestState.Loading
        render()
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%") // Need to pass pass the value between %% symbols
                    .collect { searchTaskTyped ->
                        searchTask = RequestState.Success(searchTaskTyped)
                        render()
                    }
            }
        } catch (e: Exception) {
            searchTask = RequestState.Error(e)
            render()
        }
        searchAppBarState = SearchAppBarState.TRIGGERED
        render()
    }

    /*val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)*/
    // val searchTextState: MutableState<String> = mutableStateOf("")

    fun openSearchBar() {
        searchAppBarState = SearchAppBarState.OPENED
        render()
    }

    fun closeSearchBar() {
        searchAppBarState = SearchAppBarState.CLOSED
        render()
    }

    fun defaultTextInputState() {
        searchTextInputState = ""
        render()
    }

    fun newInputTextChange(newInputVal: String) {
        searchTextInputState = newInputVal
        render()
    }

    fun readyToEmptyField() {
        closeIconState = CloseIconState.READY_TO_EMPTY_FIELD
        render()
    }

    fun readyToCloseSearchBar() {
        closeIconState = CloseIconState.READY_TO_CLOSE_SEARCH_BAR
        render()
    }

    fun returningActionToString(action: Action): String {
        return if (action.name == "DELETE") {
            "UNDO"
        } else {
            "OK"
        }
    }

    fun undoDeleteTask(
        action: Action,
        snackBarResult: SnackbarResult,
        onUndoClicked: (Action) -> Unit
    ) {
        if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
            onUndoClicked(Action.UNDO)
        }
    }

    fun databaseActionManageList(action: Action) {
        when (action) {
            Action.DELETE_ALL -> {}
            Action.UNDO -> {}
            else -> {}
        }
        this.actionListScreen = Action.NO_ACTION
        render()
    }
}
