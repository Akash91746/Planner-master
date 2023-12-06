package com.example.planner.feature_userProfile.utils

import com.example.planner.common.utils.UiText
import com.google.firebase.auth.FirebaseUser

data class UserProfileState(
    val firebaseUser: FirebaseUser? = null,
    val isLoading: Boolean = false,
    val changePasswordExpanded: Boolean = false,
    val currentPassword: String = "",
    val currentPasswordError: UiText? = null,
    val newPassword: String = "",
    val newPasswordError: UiText? = null,
    val newConfirmPassword: String = "",
    val newConfirmPasswordError: UiText? = null
)
