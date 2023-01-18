package com.kay.todopublish.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kay.todopublish.ui.screens.list.ListScreen
import com.kay.todopublish.util.Constants.LIST_ARGUMENT_KEY
import com.kay.todopublish.util.Constants.LIST_SCREEN
import com.kay.todopublish.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(
            navArgument(LIST_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        ListScreen(
            action = action,
            navigateToTaskScreen = navigateToTaskScreen,
        )
    }
}
