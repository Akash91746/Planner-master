package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.planner.feature_toDoListTask.domain.models.TrackerDetails
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@Composable
fun TrackerItemContainer(
    modifier : Modifier = Modifier,
    expanded: Boolean,
    trackerDetails: TrackerDetails,
    inputFieldEnabled: Boolean = true,
    errorMessage: String? = null,
    trackerValueOne: String = "",
    trackerValueTwo: String = "",
    onDone: () -> Unit,
    onChangeValueOne: (String) -> Unit,
    onChangeValueTwo: (String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = MaterialTheme.elevation.small
    ) {
        Column {

            content()

            AnimatedVisibility(visible = expanded) {
                Column {

                    TrackerInputField(
                        trackerData = trackerDetails,
                        valueOne = trackerValueOne,
                        valueTwo = trackerValueTwo,
                        onDone = onDone,
                        onChangeValueOne = onChangeValueOne,
                        onChangeValueTwo = onChangeValueTwo,
                        enabled = inputFieldEnabled
                    )

                    if (errorMessage != null)
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(
                                bottom = MaterialTheme.spacing.small,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small
                            )
                        )
                }
            }
        }
    }
}

@Composable
fun TrackerInputField(
    trackerData: TrackerDetails,
    valueOne: String,
    valueTwo: String = "",
    enabled: Boolean = true,
    onDone: () -> Unit,
    onChangeValueOne: (String) -> Unit,
    onChangeValueTwo: (String) -> Unit,
) {
    Row(
        modifier = Modifier.padding(MaterialTheme.spacing.small),
        horizontalArrangement = if (trackerData.titleTwo != null) Arrangement.SpaceBetween else Arrangement.End
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.48f),
            value = valueOne,
            onValueChange = onChangeValueOne,
            label = { Text(trackerData.titleOne) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            singleLine = true,
            enabled = enabled
        )

        if (trackerData.titleTwo != null)
            OutlinedTextField(
                modifier = Modifier.padding(start = MaterialTheme.spacing.small),
                value = valueTwo,
                onValueChange = onChangeValueTwo,
                label = { Text(text = trackerData.titleTwo) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { onDone() }),
                singleLine = true,
                enabled = enabled
            )
    }
}