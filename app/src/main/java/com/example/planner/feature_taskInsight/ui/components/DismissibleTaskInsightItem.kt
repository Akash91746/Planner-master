package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.TrackerDetails
import java.time.format.DateTimeFormatter

@Composable
fun DismissibleTaskInsightItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    trackerDetails: TrackerDetails?,
    onClickCheckBox: () -> Unit,
    expanded: Boolean,
    trackerValueOne: String,
    trackerValueTwo: String,
    errorMessage: String?,
    dateTimeFormatter: DateTimeFormatter,
    onChangeValueOne: (String) -> Unit,
    onChangeValueTwo: (String) -> Unit,
    onClickExpand: () ->  Unit,
    onDelete: () -> Unit
) {

    DismissibleItem(onDelete = onDelete) {

        if (trackerDetails != null) {

            RepeatTrackerTaskInsightItem(
                modifier = modifier,
                repeatTask = repeatTask,
                expanded = expanded,
                trackerValueOne = trackerValueOne,
                trackerValueTwo = trackerValueTwo,
                onClickCheckBox = onClickCheckBox,
                onChangeValueOne = onChangeValueOne,
                trackerDetails = trackerDetails,
                onChangeValueTwo = onChangeValueTwo,
                dateTimeFormatter = dateTimeFormatter,
                onClickExpand = onClickExpand,
                errorMessage = errorMessage
            )

        } else {
            RepeatTaskInsightItem(
                modifier = modifier,
                repeatTask = repeatTask,
                onClickCheckBox = onClickCheckBox,
                dateTimeFormatter = dateTimeFormatter
            )
        }
    }
}