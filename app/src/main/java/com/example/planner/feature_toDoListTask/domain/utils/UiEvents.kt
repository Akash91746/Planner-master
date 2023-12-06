package com.example.planner.feature_toDoListTask.domain.utils

sealed class UiEvents{
    data class Navigate(val path:String? = null): UiEvents()

    data class ShowSnackBar(val message: String): UiEvents()

    object ToggleBottomSheet: UiEvents()

    object HideKeyboard: UiEvents()
}
