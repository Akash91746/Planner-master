package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

val pickerColors: List<Color> = listOf(
    Color.Red,
    Color.Blue,
    Color.Black
)

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    list: List<Color> = pickerColors,
    selectedColor: Color,
    onSelect: (selectedColor: Color) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Color : ", style = MaterialTheme.typography.subtitle1)

        LazyRow {
            items(list) {

                ColorPickerItem(
                    modifier = Modifier
                        .size(32.dp),
                    color = it,
                    isSelected = selectedColor == it,
                    borderSize = 2.dp,
                    borderColor = MaterialTheme.colors.secondary
                ) {
                    onSelect(it)
                }

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            }
        }
    }
}

@Preview
@Composable
fun PreviewColorPicker() {
    PlannerTheme {
        ColorPicker(selectedColor = pickerColors[0], onSelect = {})
    }
}