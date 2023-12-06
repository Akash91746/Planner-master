package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.ui.components.ListTaskContent
import com.example.planner.ui.theme.PlannerTheme
import java.time.format.DateTimeFormatter

@Composable
fun RepeatTaskInsightItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    dateTimeFormatter: DateTimeFormatter,
    extraTrailingIcons: @Composable (() -> Unit)? = null,
    onClickCheckBox: () -> Unit,
) {
    ListTaskContent(
        modifier = modifier,
        title = repeatTask.timeStamp.format(dateTimeFormatter),
        favorite = false,
        checked = repeatTask.taskDone,
        onClickCheckBox = onClickCheckBox,
        textDecorationEnabled = false,
        extraTrailingIcons = extraTrailingIcons
    )
}

@Preview
@Composable
fun PreviewRepeatTaskInsightItem() {
    PlannerTheme {
        RepeatTaskInsightItem(
            repeatTask = RepeatTask(taskId = 1),
            dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")
        ) {

        }
    }
}