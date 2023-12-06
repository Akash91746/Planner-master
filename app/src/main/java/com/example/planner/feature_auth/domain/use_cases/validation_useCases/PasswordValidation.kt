package com.example.planner.feature_auth.domain.use_cases.validation_useCases

import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_auth.domain.utils.ValidationResult


class PasswordValidation {

    operator fun invoke(value: String): ValidationResult {

        if (value.trim().isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.required_error)
            )
        }

        val isValid = value.trim().length >= MIN_LENGTH

        return if (isValid) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_min_length_error)
            )
        }
    }

    companion object {
        const val MIN_LENGTH = 8
    }

}