package com.example.planner.feature_toDoList.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.common.ui.components.IconPicker
import com.example.planner.common.ui.components.IconPickerItem
import com.example.planner.common.ui.components.OutlinedTextFieldError
import com.example.planner.feature_toDoList.domain.utils.BottomSheetFormMode
import com.example.planner.feature_toDoList.domain.utils.FormState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun ModalBottomSheetContent(
    modifier: Modifier = Modifier,
    formState: FormState,
    onTitleChange: (String) -> Unit,
    onIconChange: (Int) -> Unit,
    onSubmit: () -> Unit
) {

    var showIconPicker by remember {
        mutableStateOf(false)
    }

    val title = remember(key1 = formState.formMode) {

        when (formState.formMode) {
            is BottomSheetFormMode.Add.List -> "Add New List"
            is BottomSheetFormMode.Add.Group -> "Add New Group"
            is BottomSheetFormMode.Update.List -> "Update List"
            is BottomSheetFormMode.Update.Group -> "Update Group"
            else -> "Add New Item"
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.medium),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
        ) {

            IconPickerItem(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.small
                    )
                    .align(Alignment.CenterVertically)
                    .clickable {
                        showIconPicker = !showIconPicker
                    },
                icon = formState.icon,
                textPadding = 12.dp,
                border = BorderStroke(width = 1.dp, color = Color.Gray)
            )

            OutlinedTextFieldError(
                value = formState.title,
                label = "Enter Title",
                singleLine = true,
                errorMessage = formState.titleErrorMessage,
                keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            ) { newValue ->
                onTitleChange(newValue)
            }

        }

        AnimatedVisibility(visible = showIconPicker) {
            IconPicker(
                selectedIcon = formState.icon,
                onChange = {
                    onIconChange(it)
                    showIconPicker = !showIconPicker
                }
            )
        }

        Button(onClick = { onSubmit() }) {
            Text(text = if (formState.formMode is BottomSheetFormMode.Add) "Add" else "Update")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewModalBottomSheetContent() {
    var formState by remember {
        mutableStateOf(
            FormState(
                title = "",
                formMode = BottomSheetFormMode.Add.Group,
                icon = PlannerIcons.DEFAULT
            )
        )
    }

    PlannerTheme {
        ModalBottomSheetContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            formState = formState,
            onTitleChange = {
                formState = formState.copy(title = it)
            },
            onIconChange = {
                formState = formState.copy(icon = it)
            }
        ) {

        }
    }
}