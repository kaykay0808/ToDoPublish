package com.kay.todopublish.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.data.repository.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

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
