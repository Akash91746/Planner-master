package com.example.planner.common.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planner.ui.theme.spacing

@Composable
fun DialogContent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    action: @Composable (RowScope) -> Unit,
    content: @Composable (ColumnScope) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.Center,
        ) {
            title()
        }

        Divider()

        Column(
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.small,
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
        ) {
            content(this)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.spacing.small,
                    horizontal = MaterialTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.End
        ) {
            action(this)
        }
    }
}