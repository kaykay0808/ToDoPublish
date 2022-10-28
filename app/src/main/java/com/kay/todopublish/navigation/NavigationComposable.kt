package com.kay.todopublish.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kay.todopublish.navigation.destinations.listComposable
import com.kay.todopublish.navigation.destinations.taskComposable
import com.kay.todopublish.util.Constants.LIST_SCREEN

// Settings for our navigation
@SuppressLint("RememberReturnType")
@Composable
fun NavigationSetup(
    navController: NavHostController
) {
    // save our backstack
    // The variable just keep track of all our composable
    val screen = remember(navController) {
        ScreenController(navController = navController)
    }

    // Calling the navHost which define the navigation graph.
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        // Define our composable build (we will create our custom destination instead of define our composable below)
        listComposable(
            navigateToTaskScreen = screen.task
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}
