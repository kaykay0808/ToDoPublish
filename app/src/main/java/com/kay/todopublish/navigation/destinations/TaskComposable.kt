package com.kay.todopublish.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.kay.todopublish.ui.screens.task.TaskScreen
import com.kay.todopublish.util.Constants.TASK_ARGUMENT_KEY
import com.kay.todopublish.util.Constants.TASK_SCREEN

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (/*Action*/) -> Unit
) {
    composable(
        // specify what argument the screen will have
        route = TASK_SCREEN,
        arguments = listOf(
            navArgument(TASK_ARGUMENT_KEY) {
                type = NavType.IntType
            }
        ),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth ->
                    -fullWidth
                },
                animationSpec = tween(durationMillis = 400) // Animation time
            )
        }
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            taskId = taskId
        )
    }
}
