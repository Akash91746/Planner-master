package com.example.planner.feature_auth.domain.utils

import com.example.planner.common.utils.UiText

data class AuthScreenState(
    val email: String = "",
    val emailErrorMessage: UiText? = null,
    val forgotPasswordEmail: String = "",
    val forgotPasswordEmailErrorMessage: UiText? = null,
    val password: String = "",
    val passwordErrorMessage: UiText? = null,
    val confirmPassword: String = "",
    val confirmPasswordErrorMessage: UiText? = null,
    val authMode: AuthMode = AuthMode.SIGN_IN,
    val isLoading: Boolean = false
)