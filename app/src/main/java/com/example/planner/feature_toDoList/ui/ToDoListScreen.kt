package com.example.planner.feature_toDoList.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.planner.feature_toDoList.domain.utils.ToDoListScreenEvents
import com.example.planner.feature_toDoList.domain.utils.UiEvents
import com.example.planner.feature_toDoList.ui.components.ModalBottomSheetContent
import com.example.planner.feature_toDoList.ui.components.ScaffoldContent
import com.example.planner.ui.theme.cornerSize
import com.example.planner.ui.theme.spacing
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ToDoListScreen(
    viewModel: ToDoListScreenViewModel,
    navigate: (path: String) -> Unit
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

    LaunchedEffect(key1 = viewModel.uiEvents) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                is UiEvents.ToggleBottomSheetForm -> {
                    if (bottomSheetState.isVisible) {
                        keyboard?.hide()
                        bottomSheetState.hide()
                        viewModel.onEvent(ToDoListScreenEvents.ResetForm)
                    } else
                        bottomSheetState.show()
                }
                is UiEvents.HideKeyboard -> {
                    keyboard?.hide()
                }
                is UiEvents.NavigateTo -> {
                    navigate(it.path)
                }
            }
        }
    }

    val state by viewModel.state

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            ModalBottomSheetContent(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                formState = state.formState,
                onTitleChange = { viewModel.onEvent(ToDoListScreenEvents.OnChangeTitle(it)) },
                onIconChange = { viewModel.onEvent(ToDoListScreenEvents.OnChangeIcon(it)) },
                onSubmit = {
                    viewModel.onEvent(ToDoListScreenEvents.OnSubmit)
                }
            )
        },
        sheetShape = MaterialTheme.shapes.large.copy(
            topEnd = MaterialTheme.cornerSize.medium,
            topStart = MaterialTheme.cornerSize.medium
        ),
        scrimColor = Color.Transparent
    ) {
        ScaffoldContent(
            state = viewModel.state.value,
            onEvent = { viewModel.onEvent(it) }
        )
    }
}

