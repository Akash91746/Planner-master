package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.common.domain.utils.PlannerIcons

data class FormState(
    val title: String = "",
    val titleErrorMessage: String? = null,
    val icon: Int = PlannerIcons.DEFAULT,
    val formMode: BottomSheetFormMode = BottomSheetFormMode.Hidden
)
