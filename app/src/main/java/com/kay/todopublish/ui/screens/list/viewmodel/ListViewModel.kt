package com.kay.todopublish.ui.screens.list.viewmodel

import android.util.Log
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.DataStoreRepository
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.ui.ViewEffects
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.CloseIconState
import com.kay.todopublish.util.RequestState
import com.kay.todopublish.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository /*( ← Inject DataStore Repository )*/
) : ViewModel() {

    private var currentList: List<TaskData> = emptyList()

    /** ============ Search Bar State ================= */
    var viewState by mutableStateOf(ListViewState())
        private set

    private var searchAppBarState = SearchAppBarState.CLOSED
    private var searchTextInputState = ""
    private var closeIconState = CloseIconState.READY_TO_EMPTY_FIELD
    private var allTask: RequestState<List<TaskData>> = RequestState.Idle
    private var searchTask: RequestState<List<TaskData>> = RequestState.Idle
    private var actionForSnackBar = Action.NO_ACTION

    // SingleTask
    private var recentlyDeletedSingleTask: TaskData? = null

    // Reading our priorities order.
    private val taskFlow = combine(
        dataStoreRepository.readSortState.map { Priority.valueOf(it) },
        repository.getAllTask,
        repository.sortByHighPriority,
        repository.sortByLowPriority
    ) { priority, allTask, sortByHighPriority, sortByLowPriority ->
        when (priority) {
            Priority.HIGH -> sortByHighPriority
            Priority.MEDIUM -> allTask
            Priority.LOW -> sortByLowPriority
            Priority.NONE -> allTask
        }
    }

    // This Init block is replaced with getAllTask() function.
    // onEach runs everytime taskFlow gets a new value. very similar to .collect 
    init {
        taskFlow.onEach { updatedList ->
            allTask = RequestState.Success(updatedList)
            // Listening updating.
            manageActions(updatedList)
            currentList = updatedList
            Log.d("updated_list", "$updatedList")
            render()
        }.catch {
            allTask = RequestState.Error(it)
            render()
        }.launchIn(viewModelScope)
    }

    private fun render() {
        viewState = ListViewState(
            searchAppBarState = searchAppBarState,
            searchTextInputState = searchTextInputState,
            closeIconState = closeIconState,
            allTask = allTask,
            searchTask = searchTask,
            actionForSnackBar = actionForSnackBar
        )
    }

    val viewEffects = ViewEffects<ListViewEffect>()

    private fun getAllTask() {
        allTask = RequestState.Loading
        render()
        try {
            viewModelScope.launch {
                repository.getAllTask.collect { updatedList ->
                    allTask = RequestState.Success(updatedList)
                    // Listening updating.
                    manageActions(updatedList)
                    currentList = updatedList
                    render()
                }
            }
        } catch (e: Exception) {
            allTask = RequestState.Error(e)
            render()
        }
    }

    // Send it to the viewEffect which add the recentlyAdded task back.
    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            recentlyDeletedSingleTask?.let { repository.addTask(taskData = it) }
        }
    }

    fun deleteSingleTaskFromList(taskData: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            recentlyDeletedSingleTask = taskData
            repository.deleteTask(taskData = taskData)
        }
    }

    fun undoDeletedTask(
        action: Action,
        snackBarResult: SnackbarResult,
        onUndoClicked: (Action) -> Unit
    ) {
        if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
            onUndoClicked(Action.UNDO)
        }
    }

    fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
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

    /** ------- search app bar states -------*/
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

    /** ------- SnackBar -------*/
    fun setActions() {
        actionForSnackBar = Action.DELETE_ALL
        render()
    }

    private fun setMessage(action: Action): String {
        return when (action) {
            Action.DELETE_ALL -> "All Task Removed"
            else -> {
                "${action.name} Task Done!"
            }
        }
    }

    fun returningActionToString(action: Action): String {
        return if (action.name == "DELETE") {
            "UNDO"
        } else {
            "OK"
        }
    }

    private fun manageActions(updatedList: List<TaskData>) {
        val actionForSnackBar = if (updatedList.isEmpty() && currentList.size > 1) {
            Action.DELETE_ALL
        } else if (currentList.isEmpty() && updatedList.size > 1) {
            Action.NO_ACTION
        } else if (currentList.size > updatedList.size) {
            Action.DELETE
        } else if (currentList.size < updatedList.size) {
            Action.ADD
        } else if (currentList != updatedList) {
            Action.UPDATE
        } else {
            Action.NO_ACTION
        }
        viewEffects.send(
            ListViewEffect.ShowSnackBar(
                action = actionForSnackBar,
                message = setMessage(actionForSnackBar),
                actionLabel = returningActionToString(action = actionForSnackBar), // "OK" - "UNDO"
                onUndoClicked = {
                    addTask()
                }
            )
        )
    }

    // Storing priorities data in to orders.
    // With this function we are going to pass the priority to the dataStoreRepository.
    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }
}

sealed interface ListViewEffect {
    data class ShowSnackBar(
        val action: Action,
        val message: String,
        val actionLabel: String,
        val onUndoClicked: (Action) -> Unit
    ) : ListViewEffect
}
