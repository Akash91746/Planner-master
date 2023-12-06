package com.example.planner.feature_taskRepeat.domain.utils

import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import com.example.planner.feature_taskRepeat.domain.use_cases.DataType

data class RepeatTaskScreenState(
    val list: List<DataType> = emptyList(),
    val quote: Quote? = null,
    val appBarTitle: UiText = UiText.StringResource(R.string.app_name)
)
