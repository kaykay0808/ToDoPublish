package com.kay.todopublish.ui.screens.list.viewmodel

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
    // private var lowPriorityTask: RequestState<List<TaskData>> = RequestState.Idle

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

    init {
        taskFlow.onEach { updatedList ->
            allTask = RequestState.Success(updatedList)
            // Listening updating.
            manageActions(updatedList)
            currentList = updatedList
            render()
        }.catch {
            allTask = RequestState.Error(it)
            render()
        }.launchIn(viewModelScope)
    }

    /*/** Priority order states */
    val lowPriorityTask: StateFlow<List<TaskData>> = repository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTask: StateFlow<List<TaskData>> = repository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )*/

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
            actionForSnackBar = actionForSnackBar
            // lowPriorityTask = lowPriorityTask
        )
    }

    val viewEffects = ViewEffects<ListViewEffect>()

    // private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    // val sortState: StateFlow<RequestState<Priority>> = _sortState
    var sortState: RequestState<Priority> = RequestState.Idle

    fun readSortState() {
        // _sortState.value = RequestState.Loading
        sortState = RequestState.Loading
        render()
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        sortState = RequestState.Success(it)
                    }
                render()
            }
        } catch (e: Exception) {
            sortState = RequestState.Error(e)
            render()
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    // Get All Task
    // private val _allTask = List<TaskData>(emptyList())
    // val allTask: StateFlow<List<TaskData>> = _allTask
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

    /*val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)*/
    // val searchTextState: MutableState<String> = mutableStateOf("")

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

    private fun setMessage(action: Action): String {
        return when (action) {
            Action.DELETE_ALL -> "All Task Removed"
            else -> {
                "${action.name} Task Done!"
            }
        }
    }

    fun setActions() {
        actionForSnackBar = Action.DELETE_ALL
        render()
    }

    private fun manageActions(updatedList: List<TaskData>) {
        val actionForSnackBar = if (updatedList.isEmpty()) {
            Action.DELETE_ALL
        } else if (currentList.isEmpty()) {
            Action.NO_ACTION
        } else if (currentList.size > updatedList.size) {
            Action.DELETE
        } else if (currentList.size < updatedList.size) {
            Action.ADD
        } else {
            Action.UPDATE
        }

        viewEffects.send(
            ListViewEffect.ShowSnackBar(
                action = actionForSnackBar,
                message = setMessage(actionForSnackBar)
            )
        )
        // if "the old" is similar to "current list" Then {
        // -> set the action to update.
        // else Current list is bigger then the old list then {
        // -> set the action to add
        // else if current list is smaller then old list then {
        // set the action to delete
        // else if current list is empty then {
        // -> set the action to delete all
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
            Action.DELETE_ALL -> {
                deleteAllTask()
            }
            Action.UNDO -> {}
            else -> {}
        }
        this.actionForSnackBar = Action.NO_ACTION
        render()
    }
}

sealed interface ListViewEffect {
    data class ShowSnackBar(val action: Action, val message: String) : ListViewEffect
}
