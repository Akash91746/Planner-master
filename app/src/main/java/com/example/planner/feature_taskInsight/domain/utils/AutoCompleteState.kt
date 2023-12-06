package com.example.planner.feature_taskInsight.domain.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AutoCompleteState<T : AutoCompleteEntity>(private val startItems: List<T>) : AutoCompleteScope<T> {

    var filteredItems by mutableStateOf(startItems)
    override var isSearching by mutableStateOf(false)

    override fun filter(query: String) {
        if (isSearching)
            filteredItems = startItems.filter { entity ->
                entity.filter(query)
            }
    }
}