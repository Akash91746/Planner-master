package com.example.planner.common.utils

import android.app.appsearch.AppSearchSchema

sealed class AppScreens(
    val defaultPath: String,
    val navPath: String,
) {
    object Home : AppScreens("/", "/")

    data class ToDoListTask(val listId: Int? = null) :
        AppScreens(
            "/tasks/{listId}", navPath = "/tasks/${listId}"
        )

    object ProgressScreen : AppScreens("/progress", "/progress")

    data class TaskInsight(private val taskId: Int) :
        AppScreens("/task-insight/{taskId}", "/task-insight/${taskId}")

    data class RepeatTaskScreen(private val date: String) :
            AppScreens("/repeat-tasks/{date}","/repeat-tasks/${date}")

    object TaskInsightScreen : AppScreens("/task-insight","/task-insight")

    object AuthScreen: AppScreens("/auth","/auth")

    object SettingScreen: AppScreens("/settings","/settings")

    object UserProfileScreen: AppScreens("/user_profile","/user_profile")
}
