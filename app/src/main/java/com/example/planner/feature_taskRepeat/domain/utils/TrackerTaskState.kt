package com.example.planner.feature_taskRepeat.domain.utils

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask

data class TrackerTaskState(
    val valueOne: String = "",
    val valueTwo: String = "",
    val errorMessage: String? = null,
    val expandedTask: RepeatTask? = null
)
