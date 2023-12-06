package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planner.common.ui.components.DividerWithTitle
import com.example.planner.feature_taskRepeat.domain.use_cases.DataType
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskScreenState
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskState
import com.example.planner.ui.theme.spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepeatTaskList(
    modifier: Modifier = Modifier,
    trackerState: TrackerTaskState,
    state: RepeatTaskScreenState,
    onEvent: (event: RepeatTaskEvents) -> Unit,
) {

    fun onTrackerTaskEvent(event: TrackerTaskEvents) =
        onEvent(RepeatTaskEvents.OnTrackerTaskEvent(event))

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {

        if (state.quote != null)
            item {
                QuoteCard(
                    data = state.quote,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.medium)
                )
            }

        items(state.list, key = { it.hashCode() }) { data ->

            if (data is DataType.Divider) {
                DividerWithTitle(
                    title = data.title,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                )
            }

            if (data is DataType.RepeatTask) {

                DismissibleRepeatTrackerTaskItem(
                    modifier = modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .animateItemPlacement(),
                    repeatTask = data.repeatTaskWithTask.repeatTask,
                    toDoListTask = data.repeatTaskWithTask.task,
                    trackerValueOne = trackerState.valueOne,
                    trackerValueTwo = trackerState.valueTwo,
                    expanded = trackerState.expandedTask?.id == data.repeatTaskWithTask.repeatTask.id,
                    errorMessage = trackerState.errorMessage,
                    onEvent = onEvent,
                    onTrackerTaskEvent = { onTrackerTaskEvent(it) }
                )
            }
        }
    }

}