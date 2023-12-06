package com.example.planner.feature_auth.domain.utils

sealed class AuthScreenEvents {

    data class OnChangeEmail(val value: String) : AuthScreenEvents()

    data class OnChangePassword(val value: String) : AuthScreenEvents()

    data class OnChangeConfirmPassword(val value: String) : AuthScreenEvents()

    data class OnChangeAuthMode(val authMode: AuthMode) : AuthScreenEvents()

    data class OnChangeForgotPasswordEmail(val value: String) : AuthScreenEvents()

    object OnClickForgetPassword : AuthScreenEvents()

    object OnSubmitSignIn : AuthScreenEvents()

    object OnSubmitSignUp : AuthScreenEvents()

    object OnSubmitForgotPassword: AuthScreenEvents()

    object OnClickBackdropScaffold: AuthScreenEvents()
}
