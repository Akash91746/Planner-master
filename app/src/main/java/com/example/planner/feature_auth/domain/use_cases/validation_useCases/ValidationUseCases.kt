package com.example.planner.feature_auth.domain.use_cases.validation_useCases

data class ValidationUseCases(
    val emailValidation: EmailValidation,
    val passwordValidation: PasswordValidation,
    val repeatPasswordValidation: RepeatPasswordValidation
)
