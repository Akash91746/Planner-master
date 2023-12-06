package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskEvents
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

@Composable
fun DismissibleRepeatTrackerTaskItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    toDoListTask: ToDoListTask,
    trackerValueOne: String,
    trackerValueTwo: String,
    expanded: Boolean,
    errorMessage: String? = null,
    onEvent: (RepeatTaskEvents) -> Unit,
    onTrackerTaskEvent: (TrackerTaskEvents) -> Unit
) {
    DismissibleRepeatTaskItem(
        modifier = modifier,
        repeatTask = repeatTask,
        toDoListTask = toDoListTask,
        trackerValueOne = trackerValueOne,
        trackerValueTwo = trackerValueTwo,
        expanded = expanded,
        errorMessage = errorMessage,
        onClickFavorite = {
            onEvent(RepeatTaskEvents.OnClickFavorite(toDoListTask))
        },
        onClickCheckBox = {
            onEvent(
                RepeatTaskEvents.OnClickCheckBox(
                    repeatTask,
                    toDoListTask
                )
            )
        },
        onChangeValueTwo = {
            onTrackerTaskEvent(TrackerTaskEvents.OnChangeValueTwo(it))
        },
        onChangeValueOne = {
            onTrackerTaskEvent(TrackerTaskEvents.OnChangeValueOne(it))
        },
        onClickExpand = {
            onTrackerTaskEvent(TrackerTaskEvents.OnClickExpand(repeatTask))
        }
    ) {
        onEvent(RepeatTaskEvents.OnDeleteRepeatTask(repeatTask))
    }
}