package com.example.planner.feature_taskRepeat.domain.utils

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask

sealed class TrackerTaskEvents {

    data class OnChangeValueOne(val value: String) : TrackerTaskEvents()

    data class OnChangeValueTwo(val value: String): TrackerTaskEvents()

    data class OnClickExpand(val repeatTask: RepeatTask): TrackerTaskEvents()
}
