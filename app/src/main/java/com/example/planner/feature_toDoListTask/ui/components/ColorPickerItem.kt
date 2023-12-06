package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.planner.ui.theme.PlannerTheme

@Composable
fun ColorPickerItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    color: Color,
    borderColor: Color = Color.LightGray,
    borderSize: Dp = 4.dp,
    selectedColor: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit,
) {
    val borderWidth = LocalDensity.current.run { borderSize.toPx() }

    var targetValue by remember {
        mutableStateOf(0f)
    }

    val animateRadius by animateFloatAsState(targetValue = targetValue)

    Canvas(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
    ) {
        val width = size.width
        val height = size.height

        val borderCircle = width.coerceAtMost(height) / 2

        val innerCircle = borderCircle - borderWidth

        drawCircle(
            color = borderColor,
            radius = borderCircle
        )

        drawCircle(
            color = color,
            radius = innerCircle
        )

        targetValue = if (isSelected) {
            borderCircle * 0.25f
        } else {
            0f
        }
        drawCircle(
            color = selectedColor,
            radius = animateRadius
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewColorPickerItem() {
    PlannerTheme {
        var isSelected by remember {
            mutableStateOf(false)
        }
        ColorPickerItem(
            color = Color.Red,
            modifier = Modifier.size(32.dp),
            borderSize = 2.dp,
            isSelected = isSelected
        ) {
            isSelected = !isSelected
        }
    }
}