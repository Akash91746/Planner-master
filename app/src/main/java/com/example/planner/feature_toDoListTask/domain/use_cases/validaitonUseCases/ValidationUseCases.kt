package com.example.planner.feature_toDoListTask.domain.use_cases.validaitonUseCases

import com.example.planner.feature_toDoList.domain.use_cases.EmptyTextValidation

data class ValidationUseCases(
    val emptyTextValidation: EmptyTextValidation,
    val repeatDialogValidation: RepeatDialogValidation
)
