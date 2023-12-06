package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.ui.components.ListTaskContent

@Composable
fun RepeatTaskItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    toDoListTask: ToDoListTask,
    extraTrailingIcon: (@Composable () -> Unit)? = null,
    onClickFavorite: () -> Unit,
    onClickCheckBox: () -> Unit,
) {
    ListTaskContent(
        modifier = modifier,
        title = toDoListTask.title,
        startColor = Color(toDoListTask.color),
        favorite = toDoListTask.favorite,
        checked = repeatTask.taskDone,
        textDecorationEnabled = true,
        onClickFavorite = onClickFavorite,
        onClickCheckBox = onClickCheckBox,
        extraTrailingIcons = extraTrailingIcon
    )
}