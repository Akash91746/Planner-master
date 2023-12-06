package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.feature_toDoListTask.domain.utils.RepeatMode
import com.example.planner.ui.theme.PlannerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepeatModeMenu(
    modifier: Modifier = Modifier,
    list: Array<RepeatMode> = RepeatMode.values(),
    value: RepeatMode?,
    onChange: (RepeatMode) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier,
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value?.name ?: "Select Repeat Mode",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )


        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            list.map {
                DropdownMenuItem(
                    onClick = {
                        onChange(it)
                        expanded = false
                    }
                ) {
                    Text(text = it.name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRepeatModeMenu() {
    PlannerTheme {
        RepeatModeMenu(value = null, onChange = {})
    }
}