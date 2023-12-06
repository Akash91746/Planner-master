package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.common.ui.components.ListItem
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@Composable
fun ListTask(
    listTask: ToDoListTask,
    modifier: Modifier = Modifier,
    enableStartColorIcon: Boolean = true,
    textDecorationEnabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    extraTrailingIcons: @Composable (() -> Unit)? = null,
    onClickCheckBox: (() -> Unit)? = null,
    onClickFavorite: (() -> Unit)? = null,
) {
    ListTaskContent(
        title = listTask.title,
        checked = listTask.taskCompleted,
        favorite = listTask.favorite,
        modifier = modifier,
        startColor = if (enableStartColorIcon) Color(listTask.color) else null,
        textDecorationEnabled = textDecorationEnabled,
        shape = shape,
        extraTrailingIcons = extraTrailingIcons,
        onClickCheckBox = onClickCheckBox,
        onClickFavorite = onClickFavorite
    )
}

@Composable
fun ListTaskContent(
    modifier: Modifier = Modifier,
    title: String,
    startColor: Color? = null,
    favorite: Boolean = false,
    checked: Boolean,
    textDecorationEnabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    extraTrailingIcons: @Composable (() -> Unit)? = null,
    onClickCheckBox: (() -> Unit)? = null,
    onClickFavorite: (() -> Unit)? = null,
) {

    ListItem(
        modifier = modifier,
        startIcon = {
            if (startColor != null)
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(MaterialTheme.spacing.small),
                    color = startColor
                )
        },
        trailing = {
            if (onClickFavorite != null)
                IconButton(
                    onClick = onClickFavorite
                ) {
                    Icon(
                        imageVector = if (favorite)
                            Icons.Rounded.Favorite else
                            Icons.Rounded.FavoriteBorder,
                        contentDescription = "Add to favorite",
                        tint = Color.Red
                    )
                }
            extraTrailingIcons?.let {
                extraTrailingIcons()
            }
        },
        elevation = MaterialTheme.elevation.small,
        shape = shape
    ) {
        if (onClickCheckBox != null)
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    onClickCheckBox()
                }
            )

        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1.copy(
                textDecoration = if (checked && textDecorationEnabled) TextDecoration.LineThrough else TextDecoration.None,
                color = if (checked && textDecorationEnabled) Color.LightGray else MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(start = if(onClickCheckBox != null) 0.dp else MaterialTheme.spacing.medium)
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
        )
    }
}

@Composable
fun DismissibleListTask(
    modifier: Modifier = Modifier,
    listTask: ToDoListTask,
    enableStartColorIcon: Boolean = true,
    textDecorationEnabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    extraTrailingIcons: @Composable (() -> Unit)? = null,
    onClickCheckBox: (() -> Unit)? = null,
    onClickFavorite: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: () -> Unit,
) {
    DismissibleItem(
        modifier = modifier,
        onDelete = onDelete,
        onEdit = onEdit
    ) {
        ListTask(
            listTask = listTask,
            enableStartColorIcon = enableStartColorIcon,
            textDecorationEnabled = textDecorationEnabled,
            shape = shape,
            extraTrailingIcons = extraTrailingIcons,
            onClickCheckBox = onClickCheckBox,
            onClickFavorite = onClickFavorite
        )
    }
}

@Preview(widthDp = 250)
@Composable
fun PreviewListTask() {
    PlannerTheme {
        val listTask = ToDoListTask(
            title = "Very Long Text, Very Long Text, Very Long Text, Very Long Text, ",
            listId = 1
        )
        ListTask(listTask = listTask, onClickFavorite = {}, onClickCheckBox = {})
    }
}