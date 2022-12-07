package com.kay.todopublish.ui.screens.list.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
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
    // TODO get all task
    private var allTask: RequestState<List<TaskData>> = RequestState.Idle

    init {
        getAllTask()
    }

    private fun render() {
        viewState = ListViewState(
            searchAppBarState = searchAppBarState,
            searchTextInputState = searchTextInputState,
            closeIconState = closeIconState,
            // TODO get all task
            allTask = allTask
        )
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
}
