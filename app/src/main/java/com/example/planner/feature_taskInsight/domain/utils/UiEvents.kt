package com.example.planner.feature_taskInsight.domain.utils

sealed class UiEvents {

    object OpenDatePicker: UiEvents()

    data class ShowSnackBar(val message: String): UiEvents()

    object HideKeyboard: UiEvents()
}
