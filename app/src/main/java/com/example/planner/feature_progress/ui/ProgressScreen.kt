package com.example.planner.feature_progress.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.feature_progress.domain.utils.ProgressScreenEvents
import com.example.planner.feature_progress.ui.components.ListContainer

@Composable
fun ProgressScreen(
    viewModel: ProgressScreenViewModel,
    navigate: (String?) -> Unit,
) {

    val state = viewModel.state.collectAsState()
    val progressScreenState = viewModel.progressScreenState.collectAsState()
    val trackerTaskState by viewModel.trackerTaskState.collectAsState()

    Scaffold(
        topBar = {
            DefaultAppBar {
                navigate(null)
            }
        }
    ) { padding ->
        ListContainer(
            progressScreenState = progressScreenState.value,
            state = state.value,
            modifier = Modifier.padding(padding),
            onChangeDate = {
                viewModel.progressScreenEvent(ProgressScreenEvents.OnChangeDate(it))
            },
            onTrackerTaskEvent = {
                viewModel.onEvent(it)
            },
            trackerState = trackerTaskState
        ) { event ->
            viewModel.onEvent(event)
        }
    }
}