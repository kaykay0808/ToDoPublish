package com.kay.todopublish.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kay.todopublish.navigation.destinations.listComposable
import com.kay.todopublish.navigation.destinations.taskComposable
import com.kay.todopublish.util.Constants.LIST_SCREEN

/** NavHost */
// Settings for our navigation
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationSetup(
    navController: NavHostController
) {
    // Calling the navHost which define the navigation graph.
    /** NavHost */
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        /** NavGraph */
        // Define our composable build (we will create our custom destination instead of define our composable below)
        listComposable(
            navigateToTaskScreen = { taskId ->
                navController.navigate("task/$taskId")
            }
        )
        taskComposable(
            navigateToListScreen = { navController.popBackStack() }
        )
    }
}
