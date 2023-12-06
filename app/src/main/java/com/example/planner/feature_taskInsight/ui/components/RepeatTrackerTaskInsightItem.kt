package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planner.R
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.ui.components.TrackerItemContainer
import com.example.planner.feature_toDoListTask.domain.models.TrackerDetails
import java.time.format.DateTimeFormatter

@Composable
fun RepeatTrackerTaskInsightItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    expanded: Boolean,
    trackerValueOne: String,
    trackerValueTwo: String,
    errorMessage: String?,
    trackerDetails: TrackerDetails,
    dateTimeFormatter: DateTimeFormatter,
    onClickCheckBox: () -> Unit,
    onChangeValueOne: (String) -> Unit,
    onChangeValueTwo: (String) -> Unit,
    onClickExpand: () -> Unit
) {
    TrackerItemContainer(
        modifier = modifier,
        expanded = expanded,
        trackerDetails = trackerDetails,
        onDone = onClickCheckBox,
        onChangeValueOne = onChangeValueOne,
        onChangeValueTwo = onChangeValueTwo,
        trackerValueOne = trackerValueOne,
        trackerValueTwo = trackerValueTwo,
        errorMessage = errorMessage,
        inputFieldEnabled = !repeatTask.taskDone
    ) {
        RepeatTaskInsightItem(
            repeatTask = repeatTask,
            onClickCheckBox = onClickCheckBox,
            dateTimeFormatter = dateTimeFormatter,
            extraTrailingIcons = {
                IconButton(onClick = onClickExpand) {
                    Icon(
                        imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.show_tracker_task_content_desc)
                    )
                }
            }
        )
    }
}