package com.example.planner.feature_toDoList.domain.utils

sealed class UiEvents {
    object ToggleBottomSheetForm: UiEvents()
    object HideKeyboard: UiEvents()
    data class NavigateTo(val path:String): UiEvents()
}
