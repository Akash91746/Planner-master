package com.example.planner.feature_taskInsight.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.common.utils.PlannerDatePicker
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightEvents
import com.example.planner.feature_taskInsight.domain.utils.UiEvents
import com.example.planner.feature_taskInsight.ui.components.ListContainer
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskInsightScreen(
    viewModel: TaskInsightViewModel,
    navigate: (String?) -> Unit,
    taskId: Int? = null,
) {

    val state by viewModel.state.collectAsState()
    val trackerState by viewModel.trackerTaskState.collectAsState()
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val scaffoldState = rememberScaffoldState()

    val currentDate = remember {
        LocalDate.now()
    }

    LaunchedEffect(key1 = taskId) {
        viewModel.onEvent(TaskInsightEvents.InitData(taskId))
    }

    LaunchedEffect(key1 = viewModel.uiEvents) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is UiEvents.OpenDatePicker -> {

                    val datePicker = PlannerDatePicker(
                        context,
                        currentDate.year,
                        currentDate.monthValue - 1,
                        currentDate.dayOfMonth
                    ) { _, year, month, day ->
                        val newEntryDate = LocalDate.of(year, month + 1, day)
                        viewModel.onEvent(TaskInsightEvents.AddNewEntry(newEntryDate))
                    }
                    datePicker.setDateRange(PlannerDatePicker.DatePickerRange.MaxDate(LocalDate.now()))

                    datePicker.show()
                }
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is UiEvents.HideKeyboard -> {
                    keyboard?.hide()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultAppBar {
                navigate(null)
            }
        },
        floatingActionButton = {
            if (state.task != null)
                ExtendedFloatingActionButton(
                    text = { Text(text = "Add Entry") },
                    onClick = { viewModel.onEvent(TaskInsightEvents.OnClickNewEntry) },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add New Entry"
                        )
                    }
                )
        }
    ) { padding ->
        ListContainer(
            modifier = Modifier.padding(padding),
            state = state,
            trackerState = trackerState,
            onEvent = { event -> viewModel.onEvent(event) },
            onTrackerEvent = { viewModel.onEvent(it) }
        )
    }
}