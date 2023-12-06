package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planner.common.utils.PlannerDatePicker
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightEvents
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightState
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskState
import com.example.planner.feature_toDoListTask.ui.components.DatePicker
import com.example.planner.feature_toDoListTask.ui.components.ListTask
import com.example.planner.ui.theme.spacing
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ListContainer(
    modifier: Modifier = Modifier,
    state: TaskInsightState,
    trackerState: TrackerTaskState,
    onTrackerEvent: (TrackerTaskEvents) -> Unit,
    onEvent: (TaskInsightEvents) -> Unit,
) {

    val dateTimeFormatter = remember {
        DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item(key = "task_search_box") {
            TaskSearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.small),
                state = state,
                onEvent = onEvent
            )
        }

        item(key = "date_selector_header") {
            Column {
                DatePicker(
                    localDate = state.fromDate,
                    title = "From ",
                    maxDate = PlannerDatePicker.DatePickerRange.MaxDate(LocalDate.now())
                ) {
                    onEvent(TaskInsightEvents.OnChangeFromDate(it))
                }

                DatePicker(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                    localDate = state.toDate,
                    title = "To ",
                    maxDate = PlannerDatePicker.DatePickerRange.MaxDate(LocalDate.now())
                ) {
                    onEvent(TaskInsightEvents.OnChangeToDate(it))
                }
            }
        }

        if (state.task != null) {
            item(key = state.task.hashCode()) {

                ListTask(
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium),
                    listTask = state.task,
                    textDecorationEnabled = false,
                    onClickFavorite = {
                        onEvent(TaskInsightEvents.OnClickFavorite(state.task))
                    }
                )
            }

            if (state.pieCharInputList != null)
                item(key = "progress_pie_chart") {
                    TaskInsightIndicator(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                        state = state,
                        onEvent = onEvent
                    )
                }

            items(state.list, key = { it.id }) { repeatTask ->

                DismissibleTaskInsightItem(
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                    repeatTask = repeatTask,
                    onClickCheckBox = { onEvent(TaskInsightEvents.OnClickCheckBox(repeatTask)) },
                    expanded = trackerState.expandedTask?.id == repeatTask.id,
                    trackerValueOne = trackerState.valueOne,
                    trackerValueTwo = trackerState.valueTwo,
                    errorMessage = trackerState.errorMessage,
                    dateTimeFormatter = dateTimeFormatter,
                    trackerDetails = state.task.tracker,
                    onChangeValueOne = { onTrackerEvent(TrackerTaskEvents.OnChangeValueOne(it)) },
                    onChangeValueTwo = { onTrackerEvent(TrackerTaskEvents.OnChangeValueTwo(it)) },
                    onClickExpand = { onTrackerEvent(TrackerTaskEvents.OnClickExpand(repeatTask)) },
                ) {
                    onEvent(TaskInsightEvents.OnDelete(repeatTask))
                }
            }


            item {
                Spacer(modifier = Modifier.padding(bottom = 36.dp))
            }
        }
    }
}
