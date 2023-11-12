package com.kay.todopublish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kay.todopublish.navigation.NavigationSetup
import com.kay.todopublish.ui.theme.ToDoPublishTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    // private val toDoViewModel: ToDoViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class) // move this outside the class??
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ToDoPublishTheme {
                // initiate navController
                navController = rememberNavController()
                NavigationSetup(
                    navController = navController
                    // toDoViewModel = toDoViewModel
                )
            }
        }
    }
}
