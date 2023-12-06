package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

@Composable
fun DismissibleRepeatTaskItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    toDoListTask: ToDoListTask,
    trackerValueOne: String,
    trackerValueTwo: String,
    expanded: Boolean,
    errorMessage: String? = null,
    onClickFavorite: () -> Unit,
    onClickCheckBox: () -> Unit,
    onClickExpand: () -> Unit,
    onChangeValueOne: (String) -> Unit,
    onChangeValueTwo: (String) -> Unit,
    onDelete: () -> Unit,
) {
    DismissibleItem(
        modifier = modifier,
        onDelete = onDelete
    ) {

        if (toDoListTask.tracker != null)
            TrackerTaskItem(
                repeatTask = repeatTask,
                toDoListTask = toDoListTask,
                onChangeValueOne = onChangeValueOne,
                onChangeValueTwo = onChangeValueTwo,
                onClickExpand = onClickExpand,
                onClickFavorite = onClickFavorite,
                onClickCheckBox = onClickCheckBox,
                trackerValueTwo = trackerValueTwo,
                trackerValueOne = trackerValueOne,
                errorMessage = errorMessage,
                expanded = expanded,
            )
        else
            RepeatTaskItem(
                repeatTask = repeatTask,
                toDoListTask = toDoListTask,
                onClickFavorite = onClickFavorite,
                onClickCheckBox = onClickCheckBox
            )
    }
}