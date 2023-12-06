package com.example.planner.feature_taskInsight.domain.utils

import androidx.compose.runtime.Stable

@Stable
interface AutoCompleteScope<T : AutoCompleteEntity> {
    var isSearching: Boolean
    fun filter(query: String)
}
