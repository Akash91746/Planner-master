package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.planner.feature_taskInsight.domain.utils.AutoCompleteEntity
import com.example.planner.feature_taskInsight.domain.utils.AutoCompleteScope
import com.example.planner.feature_taskInsight.domain.utils.AutoCompleteState

@Composable
fun <T : AutoCompleteEntity> AutoCompleteBox(
    modifier: Modifier = Modifier,
    autoCompleteState: AutoCompleteState<T>,
    listContent: @Composable (List<T>) -> Unit,
    content: @Composable AutoCompleteScope<T>.() -> Unit,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        autoCompleteState.content()

        AnimatedVisibility(visible = autoCompleteState.isSearching) {
            listContent(autoCompleteState.filteredItems)
        }
    }
}