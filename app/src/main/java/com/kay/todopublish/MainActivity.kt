package com.kay.todopublish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kay.todopublish.navigation.NavigationSetup
import com.kay.todopublish.ui.theme.ToDoPublishTheme
import com.kay.todopublish.ui.viewmodels.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val toDoViewModel: ToDoViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoPublishTheme {
                // initiate navController
                navController = rememberNavController()
                NavigationSetup(
                    navController = navController,
                    toDoViewModel = toDoViewModel
                )
            }
        }
    }
}
