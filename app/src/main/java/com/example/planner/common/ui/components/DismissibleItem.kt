package com.example.planner.common.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.planner.ui.theme.spacing


@Composable
fun DismissBackground(
    dismissState: DismissDirection? = null,
    deleteOnly: Boolean = false,
) {

    if (dismissState == null) {
        return
    }

    val transition =
        updateTransition(targetState = dismissState, label = "Dismissible Bg change animation")

    val bgColor by transition.animateColor(label = "Background color animation") { state ->
        if (state == DismissDirection.StartToEnd)
            if (deleteOnly) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.primary
            }
        else
            MaterialTheme.colors.error
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = bgColor
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (deleteOnly) Icons.Rounded.Delete else Icons.Rounded.Edit,
                contentDescription = "Edit"
            )

            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete")
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DismissibleItem(
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null,
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {

    val dismissState = rememberDismissState(
        confirmStateChange = { state ->

            if (state == DismissValue.DismissedToEnd) {
                if (onEdit != null) {
                    onEdit()
                    return@rememberDismissState false
                } else {
                    onDelete()
                }
            } else if (state == DismissValue.DismissedToStart) {
                onDelete()
            }

            true
        }
    )

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {
            DismissBackground(
                dismissState.dismissDirection,
                deleteOnly = onEdit == null
            )
        }
    ) {
        content()
    }
}