package com.example.planner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.planner.common.utils.AppScreens
import com.example.planner.feature_auth.ui.AuthScreen
import com.example.planner.feature_auth.ui.AuthViewModel
import com.example.planner.feature_progress.ui.ProgressScreen
import com.example.planner.feature_progress.ui.ProgressScreenViewModel
import com.example.planner.feature_settings.ui.SettingScreen
import com.example.planner.feature_settings.ui.SettingScreenViewModel
import com.example.planner.feature_taskInsight.ui.TaskInsightScreen
import com.example.planner.feature_taskInsight.ui.TaskInsightViewModel
import com.example.planner.feature_taskRepeat.ui.RepeatTaskScreen
import com.example.planner.feature_taskRepeat.ui.RepeatTaskViewModel
import com.example.planner.feature_toDoList.ui.ToDoListScreen
import com.example.planner.feature_toDoList.ui.ToDoListScreenViewModel
import com.example.planner.feature_toDoListTask.ui.ToDoListTaskScreen
import com.example.planner.feature_toDoListTask.ui.ToDoListTaskViewModel
import com.example.planner.feature_userProfile.ui.UserProfileScreen
import com.example.planner.feature_userProfile.ui.UserProfileViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    fun handleNavigation(path: String?) {
        path?.let {
            navController.navigate(it)
            return
        }
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = AppScreens.Home.defaultPath
    ) {

        composable(AppScreens.Home.defaultPath) {
            val viewModel = hiltViewModel<ToDoListScreenViewModel>()
            ToDoListScreen(viewModel = viewModel) {
                navController.navigate(it)
            }
        }

        composable(
            AppScreens.ToDoListTask().defaultPath,
            arguments = listOf(
                navArgument("listId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val listId = backStackEntry.arguments?.getInt("listId")
            val viewModel = hiltViewModel<ToDoListTaskViewModel>()
            ToDoListTaskScreen(viewModel = viewModel, listId = listId!!) {
                it?.let {
                    navController.navigate(it)
                    return@ToDoListTaskScreen
                }
                navController.popBackStack()
            }
        }

        composable(AppScreens.ProgressScreen.defaultPath) {
            val viewModel = hiltViewModel<ProgressScreenViewModel>()
            ProgressScreen(viewModel = viewModel) {
                it?.let {
                    navController.navigate(it)
                    return@ProgressScreen
                }

                navController.popBackStack()
            }
        }

        composable(
            AppScreens.TaskInsight(0).defaultPath,
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")

            val viewModel = hiltViewModel<TaskInsightViewModel>()

            TaskInsightScreen(
                viewModel = viewModel,
                navigate = { handleNavigation(it) },
                taskId = taskId
            )
        }

        composable(
            AppScreens.TaskInsightScreen.defaultPath
        ) {
            val viewModel = hiltViewModel<TaskInsightViewModel>()

            TaskInsightScreen(
                viewModel = viewModel,
                navigate = { handleNavigation(it) },
                taskId = null
            )
        }

        composable(
            AppScreens.RepeatTaskScreen("").defaultPath,
            arguments = listOf(
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")

            val viewModel = hiltViewModel<RepeatTaskViewModel>()

            RepeatTaskScreen(
                viewModel = viewModel,
                date = date,
                navigate = { handleNavigation(it) }
            )
        }

        composable(
            AppScreens.AuthScreen.defaultPath
        ) {
            val viewModel = hiltViewModel<AuthViewModel>()

            AuthScreen(
                viewModel = viewModel,
                navigate = ::handleNavigation
            )
        }

        composable(
            AppScreens.SettingScreen.defaultPath
        ) {
            val viewModel = hiltViewModel<SettingScreenViewModel>()
            val state by viewModel.state.collectAsState()

            SettingScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvents = viewModel.uiEvents,
                navigate = ::handleNavigation
            )
        }

        composable(
            AppScreens.UserProfileScreen.defaultPath
        ) {
            val viewModel = hiltViewModel<UserProfileViewModel>()
            val state by viewModel.state.collectAsState()

            UserProfileScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigate = ::handleNavigation,
                uiEvents = viewModel.uiEvents
            )
        }

    }
}