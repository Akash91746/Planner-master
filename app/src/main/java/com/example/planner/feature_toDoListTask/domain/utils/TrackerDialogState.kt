package com.example.planner.feature_toDoListTask.domain.utils

data class TrackerDialogState(
    val titleOne: String = "",
    val titleTwo: String = "",
    val titleOneErrorMessage: String? = null,
    val titleTwoErrorMessage: String? = null,
    val titleTwoEnabled: Boolean = false,
    val selected: Boolean = false
)
