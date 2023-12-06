package com.example.planner.feature_auth.domain.utils

import com.example.planner.common.utils.UiText

sealed class UiEvents {

    data class ShowSnackBar(val message: UiText) : UiEvents()

    object ShowForgotPasswordDialog : UiEvents()

    object HideKeyboard : UiEvents()

    object RemoveFocus: UiEvents()

    data class Navigate(val path: String?) : UiEvents()
}
