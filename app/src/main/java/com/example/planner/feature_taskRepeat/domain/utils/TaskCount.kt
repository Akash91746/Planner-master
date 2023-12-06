package com.example.planner.feature_taskRepeat.domain.utils

data class TaskCount(
    val totalTask: Int,
    val completedTask: Int
) {
    val remainingTask = totalTask - completedTask
}