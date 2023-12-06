package com.example.planner.feature_toDoListTask.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskEvents
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskFormEvents
import com.example.planner.feature_toDoListTask.domain.utils.UiEvents
import com.example.planner.feature_toDoListTask.ui.components.ModalBottomSheetContent
import com.example.planner.feature_toDoListTask.ui.components.ScaffoldContent
import com.example.planner.ui.theme.spacing
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun ToDoListTaskScreen(
    viewModel: ToDoListTaskViewModel,
    listId: Int,
    navigate: (String?) -> Unit,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { value ->
                if (value == ModalBottomSheetValue.Hidden) {
                    keyboard?.hide()
                }
                true
            }
        )
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = listId) {
        viewModel.onEvent(ToDoListTaskEvents.Init(listId))
    }

    LaunchedEffect(key1 = viewModel.uiEvents) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is UiEvents.Navigate -> {
                    navigate(event.path)
                }
                is UiEvents.ToggleBottomSheet -> {
                    if (bottomSheetState.isVisible) {
                        keyboard?.hide()
                        bottomSheetState.hide()
                        viewModel.onEvent(
                            ToDoListTaskEvents.OnToDoListTaskFormEvents(
                                ToDoListTaskFormEvents.ResetForm
                            )
                        )
                    } else
                        bottomSheetState.show()
                }
                is UiEvents.HideKeyboard -> {
                    keyboard?.hide()
                }
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    val state by viewModel.state.collectAsState()

    ModalBottomSheetLayout(
        sheetContent = {
            ModalBottomSheetContent(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                formState = state.formState,
                onRepeatDialogEvent = {
                    viewModel.onEvent(
                        ToDoListTaskEvents.OnToDoListTaskFormEvents(
                            ToDoListTaskFormEvents.OnRepeatDialogEvent(it)
                        )
                    )
                },
                selectedTask = state.selectedTask
            ) { viewModel.onEvent(ToDoListTaskEvents.OnToDoListTaskFormEvents(it)) }
        },
        sheetState = bottomSheetState,
        scrimColor = Color.Transparent
    ) {
        ScaffoldContent(
            scaffoldState = scaffoldState,
            state = state
        ) {
            viewModel.onEvent(it)
        }
    }
}