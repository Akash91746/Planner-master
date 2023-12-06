package com.example.planner.feature_taskRepeat.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskEvents
import com.example.planner.feature_taskRepeat.ui.components.RepeatTaskList

@Composable
fun RepeatTaskScreen(
    viewModel: RepeatTaskViewModel,
    navigate: (String?) -> Unit,
    date: String?,
) {
    val state by viewModel.state.collectAsState()
    val trackerState by viewModel.trackerTaskState.collectAsState()

    LaunchedEffect(key1 = date) {
        viewModel.onEvent(RepeatTaskEvents.InitData(date))
    }

    Scaffold(
        topBar = {
            DefaultAppBar(
                title = state.appBarTitle.asString(),
                onClickBack = {
                    navigate(null)
                }
            )
        }
    ) { padding ->

        RepeatTaskList(
            modifier = Modifier.padding(padding),
            state = state,
            trackerState = trackerState,
            onEvent = { viewModel.onEvent(it) },
        )

    }

}