package com.example.planner.feature_auth.domain.use_cases.validation_useCases

import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_auth.domain.utils.ValidationResult

class RepeatPasswordValidation {

    operator fun invoke(password: String, repeatPassword: String): ValidationResult {

        if (password != repeatPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.confirm_password_not_match_error)
            )
        }

        return ValidationResult(true)
    }
}