package com.example.planner.feature_toDoListGroup.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.common.ui.components.IconPickerItem
import com.example.planner.common.ui.components.ListItem
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun GroupComp(
    data: ToDoListGroup,
    isExpanded: Boolean,
    onClickAddToGroup: () -> Unit,
    onClickExpand: () -> Unit
) {

    val transition = updateTransition(targetState = isExpanded, label = "Group expand transition")

    val rotation: Float by transition.animateFloat(label = "Expand transition") {
        if (it) 180f else 0f
    }

    ListItem(
        onClick = onClickExpand,
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClickAddToGroup) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add list to group")
                }

                IconButton(onClick = onClickExpand) {
                    Icon(
                        modifier = Modifier.rotate(rotation),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Show more"
                    )
                }
            }
        },
        startIcon = {
            IconPickerItem(icon = data.icon)
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Text(
            text = data.title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupComp() {
    var expand by remember { mutableStateOf(false) }
    PlannerTheme {
        GroupComp(
            data = ToDoListGroup(title = "Title", icon = PlannerIcons.SCHOLAR_HAT),
            isExpanded = expand,
            onClickAddToGroup = {}) {
            expand = !expand
        }
    }
}