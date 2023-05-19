package com.kay.todopublish.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.kay.todopublish.navigation.destinations.listComposable
import com.kay.todopublish.navigation.destinations.taskComposable
import com.kay.todopublish.util.Constants.LIST_SCREEN

// Settings for our navigation
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationSetup(
    navController: NavHostController
    // toDoViewModel: ToDoViewModel
) {
    /**
     * I mentioned this before, but this is a bit weird to see, so just make sure
     * you understand why you are using a class to hold functions like this. I find it
     * a bit confusing and would prefer to just have the functions defined in the
     * navigation composable (look at onNavigateToFriends here https://developer.android.com/jetpack/compose/navigation#nav-from-composable)
     */
    // save our backstack
    // The variable just keep track of all our composable
    val screenDestination = remember(navController) {
        ScreenController(navController = navController)
    }
    // Calling the navHost which define the navigation graph.
    AnimatedNavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        /*splashComposable(
            navigateToListScreen = screenDestination.splash
        )*/
        // Define our composable build (we will create our custom destination instead of define our composable below)
        listComposable(
            navigateToTaskScreen = screenDestination.task
            // toDoViewModel = toDoViewModel
        )
        taskComposable(
            navigateToListScreen = screenDestination.list
        )
    }
}
