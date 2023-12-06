package com.example.planner.feature_toDoListTask.domain.use_cases.validaitonUseCases

import com.example.planner.common.utils.ValidationResult
import com.example.planner.feature_toDoListTask.domain.utils.RepeatDialogState
import com.example.planner.feature_toDoListTask.domain.utils.RepeatMode

class RepeatDialogValidation {

    operator fun invoke(state: RepeatDialogState): ValidationResult {

        if (state.repeatMode == null) return ValidationResult(
            successful = false,
            errorMessage = "Select Repeat Mode"
        )

        if (state.repeatMode == RepeatMode.WEEKLY) {
            state.weekDays.find { it.selected } ?: return ValidationResult(
                successful = false,
                errorMessage = "Select at least one day"
            )
        } else if (state.repeatMode == RepeatMode.MONTHLY ||
            state.repeatMode == RepeatMode.YEARLY
        ) {
            if(state.date == null) return ValidationResult(successful = false, errorMessage = "Date is required *")
        }

        return ValidationResult(successful = true)
    }

}