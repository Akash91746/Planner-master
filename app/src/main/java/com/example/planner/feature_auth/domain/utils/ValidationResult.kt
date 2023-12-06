package com.example.planner.feature_auth.domain.utils

import com.example.planner.common.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
