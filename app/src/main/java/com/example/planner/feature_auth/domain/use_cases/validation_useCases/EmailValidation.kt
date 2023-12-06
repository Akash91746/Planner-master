package com.example.planner.feature_auth.domain.use_cases.validation_useCases

import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_auth.domain.utils.ValidationResult


class EmailValidation {

    operator fun invoke(
        value: String,
    ): ValidationResult {

        if (value.trim().isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.required_error)
            )
        }

        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()

        return ValidationResult(
            successful = isValid,
            errorMessage = UiText.StringResource(R.string.invalid_email_error)
        )
    }
}