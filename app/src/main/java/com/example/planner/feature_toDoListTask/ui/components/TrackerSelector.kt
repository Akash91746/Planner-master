package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.common.ui.components.DialogContent
import com.example.planner.common.ui.components.OutlinedTextFieldError
import com.example.planner.feature_toDoListTask.domain.utils.TrackerDialogEvents
import com.example.planner.feature_toDoListTask.domain.utils.TrackerDialogState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun TrackerSelector(
    trackerDialogState: TrackerDialogState,
    onEvent: (TrackerDialogEvents) -> Unit
) {

    DialogContent(
        title = {
            Text(
                text = "Add Tracking Value",
                style = MaterialTheme.typography.subtitle1
            )
        },
        action = {
            OutlinedButton(
                onClick = { onEvent(TrackerDialogEvents.OnClear) },
            ) {
                Text("Clear")
            }
            Button(
                onClick = { onEvent(TrackerDialogEvents.OnSubmit) },
                modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
            ) {
                Text(text = "Done")
            }
        }
    ) {
        OutlinedTextFieldError(
            value = trackerDialogState.titleOne,
            label = "Enter Title",
            onValueChange = { onEvent(TrackerDialogEvents.OnChangeTitleOne(it)) },
            errorMessage = trackerDialogState.titleOneErrorMessage
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .heightIn(min = 64.dp)
            ) {
                AnimatedVisibility(
                    visible = trackerDialogState.titleTwoEnabled,
                ) {
                    OutlinedTextFieldError(
                        value = trackerDialogState.titleTwo,
                        onValueChange = { onEvent(TrackerDialogEvents.OnChangeTitleTwo(it)) },
                        label = "Enter Title",
                        errorMessage = trackerDialogState.titleTwoErrorMessage
                    )
                }
            }

            IconButton(onClick = { onEvent(TrackerDialogEvents.ToggleEnableTitleTwo) }) {
                Crossfade(targetState = trackerDialogState.titleTwoEnabled) {
                    if (it) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Remove Tracking Value"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add Tracking Value"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrackerSelector() {
    PlannerTheme {
        TrackerSelector(
            trackerDialogState = TrackerDialogState(),
        ) {}
    }
}