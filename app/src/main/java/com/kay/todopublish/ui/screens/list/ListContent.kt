package com.kay.todopublish.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kay.todopublish.data.models.TaskData
import com.kay.todopublish.util.Action
import com.kay.todopublish.util.RequestState
import com.kay.todopublish.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    allTask: RequestState<List<TaskData>>,
    searchedTask: RequestState<List<TaskData>>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, TaskData) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    // Logic that decide if we are going to display "searched task" or "all task"
    /**---------- Searched Task Shown ----------*/
    if (searchAppBarState == SearchAppBarState.TRIGGERED) { /* <- Set searchAppBarState to triggered in viewModel */
        if (searchedTask is RequestState.Success) {
            HandleListContentState(
                taskFromList = searchedTask.data,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    } else {
        if (allTask is RequestState.Success) {
            /** Empty task shown */
            if (allTask.data.isEmpty()) {
                EmptyContent()
            } else {
                /** All task shown */
                DisplayTask(
                    taskFromList = allTask.data,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        }
    }
}

@Composable
fun HandleListContentState(
    taskFromList: List<TaskData>,
    onSwipeToDelete: (Action, TaskData) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (taskFromList.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            taskFromList = taskFromList,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

// Function that will display task when content is not empty
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DisplayTask(
    taskFromList: List<TaskData>,
    onSwipeToDelete: (Action, TaskData) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    LazyColumn {
        items(
            items = taskFromList,
            key = { task ->
                task.id
            }
        ) { task ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            scope.launch {
                                delay(300)
                                onSwipeToDelete(Action.DELETE, task)
                            }
                            true
                        }
                        else -> false
                    }
                }
            )
            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) {
                    0f
                } else {
                    -45f
                }
            )

            // Animation
            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }
            AnimatedVisibility(
                visible = itemAppeared, // && !isDismissed?
                enter = expandVertically(
                    animationSpec = tween(
                        300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart), // specify the direction to dismiss.
                    dismissThresholds = { FractionalThreshold(fraction = 0.4f) }, // Fraction meaning we dismiss the item after 40% of the item
                    background = { SwipeRedBackground(degrees = degrees) }, // Customize background.
                    dismissContent = {
                        TaskItem(
                            modifier = Modifier.animateItemPlacement(),
                            taskData = task,
                            navigateToTaskScreen = navigateToTaskScreen
                        )
                    }
                )
            }
        }
    }
}
