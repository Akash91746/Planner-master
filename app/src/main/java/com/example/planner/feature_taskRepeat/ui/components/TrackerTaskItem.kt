package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planner.R
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

@Composable
fun TrackerTaskItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    toDoListTask: ToDoListTask,
    expanded: Boolean,
    trackerValueOne: String,
    trackerValueTwo: String,
    errorMessage: String? = null,
    onChangeValueOne: (String) -> Unit,
    onChangeValueTwo: (String) -> Unit,
    onClickExpand: () -> Unit,
    onClickFavorite: () -> Unit,
    onClickCheckBox: () -> Unit,
) {
    TrackerItemContainer(
        modifier = modifier,
        expanded = expanded,
        trackerDetails = toDoListTask.tracker!!,
        onDone = onClickCheckBox,
        onChangeValueOne = onChangeValueOne,
        onChangeValueTwo = onChangeValueTwo,
        trackerValueOne = trackerValueOne,
        trackerValueTwo = trackerValueTwo,
        errorMessage = errorMessage,
        inputFieldEnabled = !repeatTask.taskDone
    ) {
        RepeatTaskItem(
            repeatTask = repeatTask,
            toDoListTask = toDoListTask,
            onClickFavorite = onClickFavorite,
            onClickCheckBox = onClickCheckBox,
            extraTrailingIcon = {
                IconButton(
                    onClick = onClickExpand
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.show_tracker_task_content_desc)
                    )
                }
            }
        )
    }
}
