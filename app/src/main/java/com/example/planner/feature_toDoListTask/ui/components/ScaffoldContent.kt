package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import com.example.planner.R
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskEvents
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskState
import java.util.Locale

@Composable
fun ScaffoldContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    state: ToDoListTaskState,
    onEvent: (ToDoListTaskEvents) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            if (state.toDoList != null)
                FloatingActionButton(
                    onClick = { onEvent(ToDoListTaskEvents.OnClickAdd) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add new task",
                    )
                }
        },
        topBar = {
            DefaultAppBar(
                title = state.appBarTitle.asString()
            ) {
                onEvent(ToDoListTaskEvents.OnClickBack)
            }
        }
    ) { padding ->
        TaskList(
            state = state,
            onEvent = onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}