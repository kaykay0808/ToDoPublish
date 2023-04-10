package com.kay.todopublish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
        setContent {
            ToDoPublishTheme {
                // initiate navController
                navController = rememberAnimatedNavController()
                NavigationSetup(
                    navController = navController
                    // toDoViewModel = toDoViewModel
                )
            }
        }
    }
}
