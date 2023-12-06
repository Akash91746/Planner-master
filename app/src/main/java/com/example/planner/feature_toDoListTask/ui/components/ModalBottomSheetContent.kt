package com.example.planner.feature_toDoListTask.ui.components

import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.planner.common.ui.components.OutlinedTextFieldError
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.utils.RepeatDialogEvents
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskFormEvents
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskFormState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ModalBottomSheetContent(
    modifier: Modifier = Modifier,
    selectedTask: ToDoListTask?,
    formState: ToDoListTaskFormState,
    onRepeatDialogEvent: (RepeatDialogEvents) -> Unit,
    onFormEvent: (ToDoListTaskFormEvents) -> Unit,
) {

    val formTitle = remember(key1 = selectedTask) {
        if (selectedTask == null) "Add" else "Update"
    }

    val timeFormatter = remember {
        DateTimeFormatter.ofPattern("hh:mm:ss a")
    }

    val repeatChipTitle =
        remember(key1 = formState.repeatDialogState.repeatMode, key2 = formState.showRepeatDialog) {
            val repeatMode = formState.repeatDialogState.repeatMode

            if (repeatMode != null && !formState.showRepeatDialog) {
                return@remember "Repeat - " + repeatMode.toString().lowercase(Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }

            "Repeat"
        }

    val repeatModeSelected =
        remember(key1 = formState.repeatDialogState.repeatMode, key2 = formState.showRepeatDialog) {
            formState.repeatDialogState.repeatMode != null && !formState.showRepeatDialog
        }

    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = "$formTitle Task",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.medium),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )

        OutlinedTextFieldError(
            value = formState.title,
            label = "Enter Title",
            errorMessage = formState.titleErrorMessage,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    onFormEvent(ToDoListTaskFormEvents.HideKeyboard)
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        ) {
            onFormEvent(ToDoListTaskFormEvents.OnChangeTitle(it))
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        ColorPicker(
            selectedColor = Color(formState.color)
        ) {
            onFormEvent(ToDoListTaskFormEvents.OnChangeColor(it.toArgb()))
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            OutlinedFilterChip(
                selected = formState.trackerDialogState.selected,
                selectedIconDesc = "Tracker Selected",
                title = "Tracker"
            ) {
                onFormEvent(ToDoListTaskFormEvents.ToggleTrackerDialog)
            }

            OutlinedFilterChip(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                selected = repeatModeSelected,
                selectedIconDesc = "Repeat",
                title = repeatChipTitle
            ) {
                onFormEvent(ToDoListTaskFormEvents.ToggleShowRepeatDialog)
            }

            OutlinedFilterChip(
                selected = formState.reminder != null,
                selectedIconDesc = "Set Reminder",
                title = formState.reminder?.format(timeFormatter) ?: "Set Reminder"
            ) {
                val localTime = LocalTime.now()

                if (formState.reminder != null) {
                    onFormEvent(ToDoListTaskFormEvents.OnChangeReminderTime(null))
                    return@OutlinedFilterChip
                }

                val hour = localTime.hour
                val minute = localTime.minute

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, mHour: Int, mMinute: Int ->
                        onFormEvent(
                            ToDoListTaskFormEvents.OnChangeReminderTime(
                                LocalTime.of(mHour, mMinute)
                            )
                        )
                    }, hour, minute, false
                )
                timePickerDialog.window?.setElevation(8f)
                timePickerDialog.show()
            }
        }

        if (formState.showTrackerDialog)
            Dialog(
                onDismissRequest = { onFormEvent(ToDoListTaskFormEvents.ToggleTrackerDialog) },
            ) {
                Card(
                    Modifier.fillMaxWidth(),
                    elevation = MaterialTheme.elevation.small
                ) {
                    TrackerSelector(
                        trackerDialogState = formState.trackerDialogState,
                    ) {
                        onFormEvent(ToDoListTaskFormEvents.OnTrackerDialogEvent(it))
                    }
                }
            }


        if (formState.showRepeatDialog)
            Dialog(onDismissRequest = {
                onFormEvent(ToDoListTaskFormEvents.ToggleShowRepeatDialog)
            }) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = MaterialTheme.elevation.small
                ) {
                    RepeatSelector(
                        modifier = Modifier
                            .height(400.dp),
                        state = formState.repeatDialogState
                    ) {
                        onRepeatDialogEvent(it)
                    }
                }
            }


        Row(
            modifier = Modifier
                .padding(
                    bottom = MaterialTheme.spacing.small
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { onFormEvent(ToDoListTaskFormEvents.OnSubmit) }
            ) {
                Text(text = formTitle)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewModalBottomSheetContent() {
    PlannerTheme {
        ModalBottomSheetContent(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            formState = ToDoListTaskFormState(),
            onRepeatDialogEvent = {},
            selectedTask = null
        ) {}
    }
}