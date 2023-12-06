package com.example.planner.common.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@Composable
fun IconPickerItem(
    modifier: Modifier = Modifier,
    icon: Int,
    elevation: Dp = MaterialTheme.elevation.default,
    backgroundColor: Color = MaterialTheme.colors.surface,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    textPadding: Dp = MaterialTheme.spacing.extraSmall
) {
     Card(
        elevation = elevation,
        backgroundColor = backgroundColor,
        modifier = modifier,
        border = border,
        shape = shape
    ) {
         Text(
             text = String(Character.toChars(icon)),
             style = MaterialTheme.typography.h6,
             textAlign = TextAlign.Center,
             modifier = Modifier.padding(textPadding)
         )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewIconPickerItem() {
    PlannerTheme {
        IconPickerItem(icon = PlannerIcons.SCHOLAR_HAT)
    }
}
