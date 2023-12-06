package com.example.planner.feature_progress.ui.components

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
import com.example.planner.common.utils.PlannerDatePicker
import com.example.planner.feature_progress.domain.utils.ProgressScreenState
import com.example.planner.feature_taskRepeat.domain.use_cases.DataType
import com.example.planner.feature_taskRepeat.domain.utils.*
import com.example.planner.feature_taskRepeat.ui.components.DismissibleRepeatTrackerTaskItem
import com.example.planner.feature_toDoListTask.ui.components.DatePicker
import com.example.planner.feature_taskRepeat.ui.components.QuoteCard
import com.example.planner.ui.theme.spacing
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListContainer(
    modifier: Modifier = Modifier,
    progressScreenState: ProgressScreenState,
    state: RepeatTaskScreenState,
    trackerState: TrackerTaskState,
    onChangeDate: (LocalDate) -> Unit,
    onTrackerTaskEvent: (TrackerTaskEvents) -> Unit,
    onEvent: (RepeatTaskEvents) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {

        item(key = "date_selector") {
            DatePicker(
                localDate = progressScreenState.progressDate,
                onChange = {
                    onChangeDate(it)
                },
                maxDate = PlannerDatePicker.DatePickerRange.MaxDate(LocalDate.now())
            )
        }

        if (state.quote != null)
            item(key = state.quote.hashCode()) {
                QuoteCard(
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    data = state.quote
                )
            }

        item(key = progressScreenState.taskCount.hashCode()) {
            ProgressHeader(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.medium
                ),
                totalTask = progressScreenState.taskCount.totalTask,
                completedTask = progressScreenState.taskCount.completedTask,
                progressTitle = "Status"
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
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .animateItemPlacement(),
                    repeatTask = data.repeatTaskWithTask.repeatTask,
                    toDoListTask = data.repeatTaskWithTask.task,
                    trackerValueOne = trackerState.valueOne,
                    trackerValueTwo = trackerState.valueTwo,
                    expanded = trackerState.expandedTask?.id == data.repeatTaskWithTask.repeatTask.id,
                    onEvent = onEvent,
                    onTrackerTaskEvent = onTrackerTaskEvent,
                    errorMessage = trackerState.errorMessage
                )
            }

        }
    }
}