package com.example.planner.feature_userProfile.utils

import com.example.planner.common.utils.UiText

sealed class UiEvents {
    data class ShowSnackBar(val message: UiText) : UiEvents()
    object RemoveFocus: UiEvents()
}
