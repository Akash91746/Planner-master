package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.feature_toDoListTask.domain.utils.*
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing
import java.time.LocalDate

@Composable
fun RepeatSelector(
    modifier: Modifier = Modifier,
    state: RepeatDialogState,
    onEvent: (RepeatDialogEvents) -> Unit,
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Set Repeat",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )

        Divider()

        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                    RepeatModeMenu(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.repeatMode
                    ) {
                        onEvent(RepeatDialogEvents.OnChangeRepeatMode(it))
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                    AnimatedVisibility(visible = state.repeatMode == RepeatMode.WEEKLY) {
                        WeekDaysSelector(weekDayList = state.weekDays) {
                            onEvent(RepeatDialogEvents.OnChangeWeekDaySelection(it))
                        }
                    }

                    AnimatedVisibility(
                        visible = state.repeatMode == RepeatMode.MONTHLY ||
                                state.repeatMode == RepeatMode.YEARLY
                    ) {
                        if (state.repeatMode != null)
                            DatePicker(
                                localDate = state.date ?: LocalDate.now(),
                                repeatMode = state.repeatMode,
                                onChange = {
                                    onEvent(RepeatDialogEvents.OnChangeDate(it))
                                }
                            )
                    }
                }

                state.errorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { onEvent(RepeatDialogEvents.OnClickClear) },
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                ) {
                    Text(text = "Clear")
                }

                Button(onClick = { onEvent(RepeatDialogEvents.OnSubmit) }) {
                    Text(text = "Done")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRepeatSelector() {
    PlannerTheme {
        var repeatMode: RepeatMode? by remember {
            mutableStateOf(null)
        }
        RepeatSelector(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            state = RepeatDialogState(repeatMode = repeatMode)
        ) {
            when (it) {
                is RepeatDialogEvents.OnChangeRepeatMode -> {
                    repeatMode = it.repeatMode
                }
                else -> {}
            }
        }
    }
}