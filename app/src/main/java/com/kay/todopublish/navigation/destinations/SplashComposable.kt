package com.kay.todopublish.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.kay.todopublish.ui.screens.splash.SplashScreen
import com.kay.todopublish.util.Constants.SPLASH_SCREEN

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight ->
                    -fullHeight
                },
                animationSpec = tween(300)
            )
        }
    ) {
        SplashScreen(
            navigateToListScreen = navigateToListScreen
        )
    }
}
