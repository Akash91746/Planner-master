package com.example.planner.feature_taskInsight.domain.utils

data class LineGraphInput(
    val x: LineGraphInputData,
    val y: LineGraphInputData,
)

data class LineGraphInputData(
    val value: Int,
    val title: String? = null
)
