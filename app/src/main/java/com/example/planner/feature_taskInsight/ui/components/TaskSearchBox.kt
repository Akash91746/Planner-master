package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightEvents
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightState
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskSearchBox(
    modifier : Modifier = Modifier,
    state: TaskInsightState,
    onEvent: (TaskInsightEvents) -> Unit,
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    fun onSelect(task: ToDoListTask) {
        onEvent(
            TaskInsightEvents.OnSelectTask(
                task
            )
        )
        focusManager.clearFocus()
        keyboard?.hide()
    }

    AutoCompleteBox(
        modifier = modifier,
        autoCompleteState = state.autoCompleteState,
        listContent = { list ->
            Card(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                elevation = MaterialTheme.elevation.small
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(0.dp, TextFieldDefaults.MinHeight * 3)
                ) {
                    items(list, key = { it.value.id }) {
                        ListItem(
                            modifier = Modifier.clickable {
                                onSelect(it.value)
                            }
                        ) {
                            Text(text = it.value.title)
                        }
                    }
                }
            }
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = state.searchQuery,
            onValueChange = {
                onEvent(TaskInsightEvents.OnChangeSearchQuery(it))
            },
            placeholder = {
                Text(text = "Search Repeating Task..")
            },
            trailingIcon = {
                IconButton(onClick = {
                    onEvent(TaskInsightEvents.OnClearSearchQuery)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Clear Search Query"
                    )
                }
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                keyboard?.hide()
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}