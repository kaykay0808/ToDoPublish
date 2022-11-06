package com.kay.todopublish.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import com.kay.todopublish.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {
    /** ============ Search Bar State ================= */
    var viewState by mutableStateOf(ToDoViewState())
        private set

    private var searchAppBarState = SearchAppBarState.CLOSED
    private var searchTextInputState = ""

    private fun render() {
        viewState = ToDoViewState(
            searchAppBarState = searchAppBarState,
            searchTextInputState = searchTextInputState
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
    }

    fun newInputTextChange(newInputVal: String) {
        searchTextInputState = newInputVal
        render()
    }

    // Get All Task
    private val _allTask = MutableStateFlow<List<TaskData>>(emptyList())
    val allTask: StateFlow<List<TaskData>> = _allTask
    fun getAllTask() {
        viewModelScope.launch {
            repository.getAllTask.collect {
                _allTask.value = it
            }
        }
    }
}
