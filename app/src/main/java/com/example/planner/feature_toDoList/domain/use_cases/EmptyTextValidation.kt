package com.example.planner.feature_toDoList.domain.use_cases

import com.example.planner.common.utils.ValidationResult

class EmptyTextValidation {

    operator fun invoke(value: String): ValidationResult {
        val success = value.trim().isNotEmpty()
        return if(success) ValidationResult(successful = true) else
            ValidationResult(successful = false,"Required *")
    }
}