package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlinedFilterChip(
    modifier: Modifier =Modifier,
    selected: Boolean,
    imageVector: ImageVector = Icons.Rounded.Close,
    selectedIconDesc: String,
    title:String,
    onClick: () -> Unit,
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        selectedIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = selectedIconDesc,
                modifier = Modifier.requiredSize(ChipDefaults.SelectedIconSize)
            )
        },
        border = ChipDefaults.outlinedBorder,
        colors = ChipDefaults.outlinedFilterChipColors()
    ) {
        Text(text = title)
    }
}