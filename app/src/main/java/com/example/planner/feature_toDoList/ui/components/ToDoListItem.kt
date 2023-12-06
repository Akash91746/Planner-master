package com.example.planner.feature_toDoList.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.planner.common.ui.components.IconPickerItem
import com.example.planner.common.ui.components.ListItem
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@Composable
fun ToDoListItem(
    toDoList: ToDoList,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier,
        content = {
            Text(
                text = toDoList.title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.small).weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        startIcon = {
            IconPickerItem(icon = toDoList.icon)
        },
        trailing = {
            if (toDoList.taskRemaining > 0)
                Card(
                    elevation = MaterialTheme.elevation.extraSmall,
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                ) {
                    Text(
                        text = if (toDoList.taskRemaining >= 10) "9+" else "${toDoList.taskRemaining}",
                        modifier = Modifier.padding(
                            vertical = MaterialTheme.spacing.extraSmall,
                            horizontal = MaterialTheme.spacing.small
                        )
                    )
                }
        },
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.background
    )
}