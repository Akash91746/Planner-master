package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.common.ui.components.ListItem
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing
import java.time.format.DateTimeFormatter

@Composable
fun TaskInsightItem(
    modifier: Modifier = Modifier,
    repeatTask: RepeatTask,
    dateTimeFormatter: DateTimeFormatter,
    trailingIcon: @Composable ((RowScope) -> Unit)? = null,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier,
        onClick = onClick,
        trailing = trailingIcon,
        elevation = MaterialTheme.elevation.small,
        shape = MaterialTheme.shapes.medium
    ) {
        Checkbox(
            checked = repeatTask.taskDone,
            onCheckedChange = { onClick() }
        )

        Text(
            text = repeatTask.timeStamp.format(dateTimeFormatter),
            style = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small),
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun PreviewTaskInsightItem() {
    PlannerTheme {
        TaskInsightItem(
            repeatTask = RepeatTask(taskId = 1),
            dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")
        ) {}
    }
}