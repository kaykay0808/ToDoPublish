package com.kay.todopublish.navigation

import androidx.navigation.NavHostController

// Class which handle our navigation
class ScreenController(navController: NavHostController) {
    /** LIST-SCREEN TO TASK-SCREEN  (List route?)*/
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId") // <- pass in our constants later
    }

    /** TASK-SCREEN TO LIST-SCREEN (Task Route)*/
    val list: (/*Action*/) -> Unit = { // action ->
        // make our Enum action to navigate
        navController.popBackStack()
        /*navigate("list/${action.name}") {
            // Define which destination we are going to.
            popUpTo(LIST_SCREEN) {
                inclusive = true
            } /*Whenever we navigate from our task  to our list, we pop up to our list screen */
        }*/
    }
}
