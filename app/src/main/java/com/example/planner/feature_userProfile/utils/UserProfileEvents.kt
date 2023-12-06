package com.example.planner.feature_userProfile.utils

sealed class UserProfileEvents {


    data class OnChangeCurrentPassword(val value: String) : UserProfileEvents()

    data class OnChangeNewPassword(val value: String): UserProfileEvents()

    data class OnChangeConfirmNewPassword(val value: String): UserProfileEvents()

    object OnClickVerifyEmail: UserProfileEvents()

    object ToggleUpdatePassword : UserProfileEvents()

    object OnClickResetCurrentPassword : UserProfileEvents()

    object OnSubmitChangePassword: UserProfileEvents()

    object OnClickScaffold: UserProfileEvents()
}
