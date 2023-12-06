package com.example.planner.common.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun IconPicker(
    modifier: Modifier = Modifier,
    icons: List<Int> = PlannerIcons.LIST,
    selectedIcon: Int,
    onChange: (icon: Int) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(48.dp),
        modifier = modifier
    ) {
        itemsIndexed(icons) { _, item ->
            IconPickerItem(
                icon = item,
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.small)
                    .clickable { onChange(item) },
                backgroundColor = if (item == selectedIcon) Color.LightGray else MaterialTheme.colors.surface,
                textPadding = MaterialTheme.spacing.small
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewIconPicker() {
    var selectedIcon by remember{
        mutableStateOf(PlannerIcons.DEFAULT)
    }
    PlannerTheme {
        IconPicker(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            selectedIcon = selectedIcon
        ) {
            selectedIcon = it
        }
    }
}
